package com.imaginecup.morpheus.character.dao;

import com.imaginecup.morpheus.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
