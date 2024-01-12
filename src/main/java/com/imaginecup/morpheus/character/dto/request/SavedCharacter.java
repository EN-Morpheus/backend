package com.imaginecup.morpheus.character.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavedCharacter {

    private String name;
    private CharacterCreationForm characterCreationForm;
    private String imageUrl;
    private String revisedPrompt;

}
