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
import com.imaginecup.morpheus.utils.dto.DetailResponse;
import com.imaginecup.morpheus.utils.dto.Response;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
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

        memberRepository.findByMemberId(SecurityUtils.getCurrentMemberId())
                .ifPresentOrElse(
                        member -> {
                            Picture image = s3Service.getImage(imageFile, savedCharacter.getName());

                            Character character = Character.builder()
                                    .member(member)
                                    .picture(image)
                                    .introduction(savedCharacter.getCharacterCreationForm().getIntroduction())
                                    .personality(savedCharacter.getCharacterCreationForm().getPersonality())
                                    .revisedPrompt(savedCharacter.getRevisedPrompt())
                                    .name(savedCharacter.getName())
                                    .prompt(Parser.parseSaveCharacterPrompt(savedCharacter.getCharacterCreationForm()))
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
        respone.of("result", "SUCCESS");

        List<Character> characters = characterRepository.findByMemberMemberId(SecurityUtils.getCurrentMemberId());

        if (characters.size() == 0) {
            respone.of("error", DetailResponse.builder().code(202).message("조회 가능한 캐릭터가 없습니다.").build());
            return new ResponseEntity<>(respone, HttpStatus.ACCEPTED);
        }

        List<CharacterInfo> characterList = new ArrayList<>();
        for(Character character : characters) {
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
        System.out.println(imageBody);
        JSONObject imageData = Parser.extractDataFromResponse(imageBody);
        System.out.println(imageData.toString());

        Map<String, Object> imageDataMap = new HashMap<>();
        JSONArray dataArray = imageData.getJSONArray("data");
        imageDataMap.put("data", dataArray.toList());

        Response response = new Response();
        response.of("result", "SUCCESS");
        response.of("image url", imageDataMap);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
