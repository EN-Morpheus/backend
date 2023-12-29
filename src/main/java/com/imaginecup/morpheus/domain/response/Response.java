package com.imaginecup.morpheus.domain.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Response {

    private Integer code;
    private String message;

}
