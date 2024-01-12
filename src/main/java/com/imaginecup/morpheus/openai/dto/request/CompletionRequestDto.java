package com.imaginecup.morpheus.openai.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompletionRequestDto {

    private String model;

    private String prompt;

    private float temperature;

}
