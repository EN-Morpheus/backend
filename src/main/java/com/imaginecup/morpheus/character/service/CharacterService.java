package com.imaginecup.morpheus.character.service;

import com.imaginecup.morpheus.character.dto.request.SavedCharacter;
import com.imaginecup.morpheus.character.dto.response.CharacterInfo;
import com.imaginecup.morpheus.utils.dto.Response;
import org.springframework.http.ResponseEntity;

public interface CharacterService {

    void increaseCharacterRoom();

    Response addCharacter(SavedCharacter savedCharacter) throws Exception;

    ResponseEntity<Response> lookup();

    ResponseEntity<Response> createImage(String prompt);

    void deleteCharacter(Long characterId);

    CharacterInfo pickCharacter(Long characterId);

}
