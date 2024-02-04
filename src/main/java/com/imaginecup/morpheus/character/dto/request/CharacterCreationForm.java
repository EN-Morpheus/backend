package com.imaginecup.morpheus.character.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CharacterCreationForm {

    @Schema(description = "눈에 띄는 특징", defaultValue = "점, 주근깨, 안경")
    private String introduction;

    private String name;
    private String personality;

    @Schema(description = "애니메이션 스타일", defaultValue = "DISNEY, PIXAR, GHIBLI, AMERICAN_CARTOON, SIMPSON, KOREAN_WEBTOON")
    private String animationStyle;

    @Schema(description = "인종, 종", defaultValue = "girl, boy, name of animal species, (white, african american, asian, latina, hispanic)")
    private String species;

    @Schema(description = "털, 머리카락에 대한 설명", defaultValue = "rea hair, long hair, curly hair")
    private String furDescription;

    private String clothes;

    @Schema(description = "눈", defaultValue = "red eyes")
    private String eyes;

}
