package com.imaginecup.morpheus.domain.response.token;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}