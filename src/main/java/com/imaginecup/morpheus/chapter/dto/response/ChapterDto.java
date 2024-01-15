package com.imaginecup.morpheus.chapter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChapterDto {

    private String story;

    private String plot;

    private String background;

    private String narrativeText;

    private int order;

    private String imageUrl;

}
