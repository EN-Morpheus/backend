package com.imaginecup.morpheus.utils.token.dto.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}