package com.imaginecup.morpheus.chapter.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class chapterResponseDto {

    private String story;

    private String plot;

    private String background;

    private String narrativeText;

    private int order;

    private String imageUrl;

}
