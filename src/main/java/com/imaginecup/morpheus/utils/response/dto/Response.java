package com.imaginecup.morpheus.utils.response.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Response {

    public Response() {
        response = new HashMap<>();
    }

    public void of(String value1, Object value2) {
        response.put(value1, value2);
    }

    private Map<String, Object> response;

}
