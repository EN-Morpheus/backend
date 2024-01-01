package com.imaginecup.morpheus.character.dao;

import com.imaginecup.morpheus.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findByMemberMemberId(String memberId);

}
