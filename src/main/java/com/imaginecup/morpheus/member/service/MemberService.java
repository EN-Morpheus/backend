package com.imaginecup.morpheus.member.service;

import com.imaginecup.morpheus.member.dto.request.JoinDto;
import com.imaginecup.morpheus.token.dto.request.ReissuedTokenDto;
import com.imaginecup.morpheus.token.dto.response.TokenInfo;
import com.imaginecup.morpheus.utils.dto.Response;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    public TokenInfo login(String memberId, String password);
    public Response join(JoinDto joinDto);
    public ResponseEntity<Response> checkDuplicatedId(String id);
    public TokenInfo reissue(ReissuedTokenDto reissuedTokenDto);
}
