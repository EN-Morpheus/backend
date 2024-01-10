package com.imaginecup.morpheus.chapter.dto;

import com.imaginecup.morpheus.picture.domain.Picture;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class chapterResponse {

    private String story;

    private String plot;

    private String background;

    private String narrativeText;

    private int order;

    private String imageUrl;

}
