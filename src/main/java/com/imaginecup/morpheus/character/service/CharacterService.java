package com.imaginecup.morpheus.character.service;

import com.imaginecup.morpheus.character.dto.request.CharacterInfoDto;
import com.imaginecup.morpheus.utils.dto.Response;

public interface CharacterService {

    void increaseCharacterRoom();

    Response addCharacter(CharacterInfoDto characterInfoDto);

}
