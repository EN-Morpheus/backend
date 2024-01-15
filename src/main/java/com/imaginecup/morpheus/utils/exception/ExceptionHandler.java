package com.imaginecup.morpheus.utils.exception;

import com.imaginecup.morpheus.utils.dto.DetailResponse;
import com.imaginecup.morpheus.utils.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {

    public static ResponseEntity create500Error(Response response, Exception e) {
        response.of("result", "FAIL");
        response.of("error", DetailResponse.builder().code(500).message(e.getMessage()).build());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
