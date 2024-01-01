package com.imaginecup.morpheus.character.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CreadtedCharacter {

    private Long seed;
    private String prompt;
    private MultipartFile image;
    private String name;

}
