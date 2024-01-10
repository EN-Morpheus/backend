package com.imaginecup.morpheus.character.service;

import com.imaginecup.morpheus.character.dto.request.CharacterCreationForm;
import com.imaginecup.morpheus.character.dto.request.CreadtedCharacter;
import com.imaginecup.morpheus.character.dto.response.CharacterInfo;
import com.imaginecup.morpheus.utils.dto.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CharacterService {

    void increaseCharacterRoom();

    Response addCharacter(CreadtedCharacter creadtedCharacter);

    ResponseEntity<Response> lookup();

    ResponseEntity<Response> createImage(String prompt);

}
