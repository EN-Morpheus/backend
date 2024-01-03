package com.imaginecup.morpheus.character.dto.request;

import lombok.Getter;

@Getter
public class ChoosenCharacter {

    private Long seed;
    private String prompt;
    private String name;

}
