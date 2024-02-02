package com.imaginecup.morpheus.fairy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApproximateStoryDto {

    private String title;
    private String story;
    private String subjectMatter;
    private String plot;
    private String characters;
    private String linguisticExpression;

}
