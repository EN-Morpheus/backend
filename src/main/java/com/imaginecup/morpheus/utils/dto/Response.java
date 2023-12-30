package com.imaginecup.morpheus.utils.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class Response {

    public Response() {
    }

    public void of(String value1, Object value2) {
        response.put(value1, value2);
    }

    private Map<String, Object> response;

}
