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

    public ApproximateStoryDto(JSONObject responseJson) {
        ApproximateStoryDto.
                builder()
                .title(responseJson.getString("title"))
                .story(responseJson.getString("story"))
                .subjectMatter(responseJson.getString("subjectMatter"))
                .plot(responseJson.getString("plot"))
                .characters(responseJson.getString("characters"))
                .linguisticExpression(responseJson.getString("linguisticExpression"))
                .build();
    }

}
