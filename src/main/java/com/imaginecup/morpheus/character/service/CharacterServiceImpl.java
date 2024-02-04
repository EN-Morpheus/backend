package com.imaginecup.morpheus.character.service;

import com.imaginecup.morpheus.character.dao.CharacterRepository;
import com.imaginecup.morpheus.character.domain.Character;
import com.imaginecup.morpheus.character.dto.request.SavedCharacter;
import com.imaginecup.morpheus.character.dto.response.CharacterInfo;
import com.imaginecup.morpheus.openai.service.OpenaiService;
import com.imaginecup.morpheus.member.dao.MemberRepository;
import com.imaginecup.morpheus.member.domain.Member;
import com.imaginecup.morpheus.picture.domain.Picture;
import com.imaginecup.morpheus.s3.service.S3Service;
import com.imaginecup.morpheus.utils.ImageSaver;
import com.imaginecup.morpheus.utils.Parser;
import com.imaginecup.morpheus.utils.SecurityUtils;
import com.imaginecup.morpheus.utils.response.dto.DetailResponse;
import com.imaginecup.morpheus.utils.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final CharacterRepository characterRepository;
    private final OpenaiService openaiService;

    @Override
    public void increaseCharacterRoom() {
        Optional<Member> member = memberRepository.findByMemberId(SecurityUtils.getCurrentMemberId());

        if (member.get().getCountOfCharacter() >= 10) {
            throw new RuntimeException("캐릭터의 방은 10개를 초과할 수 없습니다.");
        }

        Member purchaser = Member.builder()
                .countOfCharacter(member.get().getCountOfCharacter() + 1)
                .memberId(member.get().getMemberId())
                .password(member.get().getPassword())
                .email(member.get().getEmail())
                .name(member.get().getName())
                .authority(member.get().getAuthority())
                .build();

        memberRepository.save(purchaser);
    }

    @Override
    public Response addCharacter(SavedCharacter savedCharacter) throws Exception {
        MultipartFile imageFile = ImageSaver.downloadImageAsMultipartFile(savedCharacter.getImageUrl());
        String characterPrompt = Parser.parseSaveCharacterPromptDB(savedCharacter.getCharacterCreationForm());

        memberRepository.findByMemberId(SecurityUtils.getCurrentMemberId())
                .ifPresentOrElse(
                        member -> {
                            Picture image = s3Service.getImage(imageFile, savedCharacter.getCharacterCreationForm().getName());

                            Character character = Character.builder()
                                    .name(savedCharacter.getCharacterCreationForm().getName())
                                    .member(member)
                                    .picture(image)
                                    .introduction(savedCharacter.getCharacterCreationForm().getIntroduction())
                                    .personality(savedCharacter.getCharacterCreationForm().getPersonality())
                                    .revisedPrompt(savedCharacter.getRevisedPrompt())
                                    .prompt(characterPrompt)
                                    .animationStyle(savedCharacter.getCharacterCreationForm().getAnimationStyle())
                                    .clothes(savedCharacter.getCharacterCreationForm().getClothes())
                                    .eyes(savedCharacter.getCharacterCreationForm().getEyes())
                                    .furDescription(savedCharacter.getCharacterCreationForm().getFurDescription())
                                    .species(savedCharacter.getCharacterCreationForm().getSpecies())
                                    .build();

                            characterRepository.save(character);
                        },
                        () -> {
                            throw new RuntimeException("유효한 유저 정보가 아닙니다.");
                        }
                );
        Response response = new Response();
        response.of("result", "SUCCESS");
        response.of("code", DetailResponse.builder().code(202).message("캐릭터 생성 완료").build());

        return response;
    }

    @Override
    public ResponseEntity<Response> lookup() {
        Response respone = new Response();

        List<Character> characters = characterRepository.findByMemberMemberId(SecurityUtils.getCurrentMemberId());

        if (characters.size() == 0) {
            respone.of("result", "FAIL");
            respone.of("error", DetailResponse.builder().code(202).message("조회 가능한 캐릭터가 없습니다.").build());
            return new ResponseEntity<>(respone, HttpStatus.ACCEPTED);
        }

        respone.of("result", "SUCCESS");

        List<CharacterInfo> characterList = new ArrayList<>();
        for (Character character : characters) {
            CharacterInfo characterInfo = CharacterInfo.builder()
                    .image(character.getPicture().getUrl())
                    .name(character.getName())
                    .id(character.getId())
                    .build();

            characterList.add(characterInfo);
        }

        respone.of("code", characterList);

        return new ResponseEntity<>(respone, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> createImage(String prompt) {
        String imageBody = openaiService.generatePicture(prompt);

        List<Object> imageData = Parser.extractDataFromResponse(imageBody);


        Map<String, Object> imageDataMap = new HashMap<>();
        imageDataMap.put("data", imageData);

        Response response = new Response();
        response.of("result", "SUCCESS");
        response.of("image_url", imageDataMap);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void deleteCharacter(Long characterId) {
        characterRepository.findById(characterId)
                .ifPresentOrElse(
                        character -> {
                            if (character.getMember().getMemberId().equals(SecurityUtils.getCurrentMemberId())) {
                                characterRepository.delete(character);
                            } else {
                                throw new RuntimeException("로그인 중인 유저의 캐릭터 ID가 아닙니다.");
                            }
                        },
                        () -> {
                            throw new RuntimeException("유효한 캐릭터 ID가 아닙니다.");
                        }
                );
    }

    @Override
    public CharacterInfo pickCharacter(Long characterId) {
        Optional<Character> character = characterRepository.findById(characterId);
        CharacterInfo characterInfo;

        if (character.isPresent()) {
            characterInfo = CharacterInfo.builder()
                    .id(character.get().getId())
                    .name(character.get().getName())
                    .image(character.get().getPicture().getUrl())
                    .build();
        } else {
            throw new RuntimeException("유효한 캐릭터의 ID가 아닙니다.");
        }

        return characterInfo;
    }

}
