package com.imaginecup.morpheus.utils.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Response {

    public Response() {
        response = new HashMap<>();
    }

    public static void of(String value1, Object value2) {
        response.put(value1, value2);
    }

    private static Map<String, Object> response;

}
