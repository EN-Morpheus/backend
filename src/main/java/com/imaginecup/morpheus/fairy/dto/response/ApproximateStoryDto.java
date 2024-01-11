package com.imaginecup.morpheus.fairy.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApproximateStoryDto {

    private String story;
    private String subjectMatter;
    private String plot;
    private String characters;
    private String linguisticExpression;

}
