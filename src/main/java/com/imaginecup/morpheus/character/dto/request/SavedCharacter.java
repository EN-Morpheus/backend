package com.imaginecup.morpheus.character.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedCharacter {

    private CharacterCreationForm characterCreationForm;
    private String imageUrl;
    private String revisedPrompt;

}
