package com.imaginecup.morpheus.character.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CharacterInfo {

    private Long id;
    private String image;
    private String name;

}
