package com.imaginecup.morpheus.character.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class CharacterInfoDto {

    private Long seed;
    private String prompt;
    private MultipartFile image;
    private String name;

}
