package com.imaginecup.morpheus.character.service;

import com.imaginecup.morpheus.character.dao.CharacterRepository;
import com.imaginecup.morpheus.character.domain.Character;
import com.imaginecup.morpheus.character.dto.request.CharacterInfoDto;
import com.imaginecup.morpheus.member.dao.MemberRepository;
import com.imaginecup.morpheus.member.domain.Member;
import com.imaginecup.morpheus.picture.domain.Picture;
import com.imaginecup.morpheus.s3.service.S3Service;
import com.imaginecup.morpheus.utils.SecurityUtils;
import com.imaginecup.morpheus.utils.dto.DetailResponse;
import com.imaginecup.morpheus.utils.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    private final CharacterRepository characterRepository;

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
    public Response addCharacter(CharacterInfoDto characterInfoDto) {
        memberRepository.findByMemberId(SecurityUtils.getCurrentMemberId())
                .ifPresentOrElse(
                        member -> {
                            Picture image = s3Service.uploadMedia(characterInfoDto.getImage());

                            Character character = Character.builder()
                                    .member(member)
                                    .picture(image)
                                    .prompt(characterInfoDto.getPrompt())
                                    .seed(characterInfoDto.getSeed())
                                    .build();

                            characterRepository.save(character);
                        },
                        () -> { throw new RuntimeException("유효한 유저 정보가 아닙니다."); }
                );
        Response response = new Response();
        response.of("result", "SUCCESS");
        response.of("code", DetailResponse.builder().code(202).message("캐릭터 생성 완료").build());

        return response;
    }

}
