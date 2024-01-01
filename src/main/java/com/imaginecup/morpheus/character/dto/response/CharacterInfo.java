package com.imaginecup.morpheus.character.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class CharacterInfo {

    private String image;
    private String name;

}
