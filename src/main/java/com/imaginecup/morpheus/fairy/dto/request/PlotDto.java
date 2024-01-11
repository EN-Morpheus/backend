package com.imaginecup.morpheus.fairy.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlotDto {

    private String topic;
    private Long characterId;
}
