package com.imaginecup.morpheus.utils.response;

import com.imaginecup.morpheus.utils.response.dto.DetailResponse;
import com.imaginecup.morpheus.utils.response.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static ResponseEntity create500Error(Response response, Exception e) {
        response.of("result", "FAIL");
        response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity create400Error(Response response, Exception e) {
        response.of("result", "FAIL");
        response.of("error", DetailResponse.builder().code(400).message(e.getMessage()).build());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity create202Response(Response response) {
        response.of("result", "SUCCESS");
        response.of("code", DetailResponse.builder().code(202).build());

        return new ResponseEntity(response, HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity create200Response(Response response, Object object) {
        response.of("result", "SUCCESS");
        response.of("code", object);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
