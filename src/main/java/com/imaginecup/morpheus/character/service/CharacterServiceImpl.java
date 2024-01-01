package com.imaginecup.morpheus.character.service;

import com.imaginecup.morpheus.character.dao.CharacterRepository;
import com.imaginecup.morpheus.member.dao.MemberRepository;
import com.imaginecup.morpheus.member.domain.Member;
import com.imaginecup.morpheus.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final MemberRepository memberRepository;

    @Override
    public void increaseCharacterRoom() {
        Optional<Member> member = memberRepository.findByMemberId(SecurityUtils.getCurrentMemberId());

        if(member.get().getCountOfCharacter() >= 10) {
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

}
