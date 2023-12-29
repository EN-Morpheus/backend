package com.imaginecup.morpheus.domain.request.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReissuedTokenDto {

    @Schema(description = "현재 사용 중인 만료된 AccessToken")
    private String accessToken;

    @Schema(description = "사용 중인 AccessToken을 발급받을 때 같이 발급받은 RefreshToken")
    private String refreshToken;

}
