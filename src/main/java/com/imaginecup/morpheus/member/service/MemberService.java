package com.imaginecup.morpheus.member.service;

import com.imaginecup.morpheus.member.dto.request.JoinDto;
import com.imaginecup.morpheus.utils.token.dto.request.ReissuedTokenDto;
import com.imaginecup.morpheus.utils.token.dto.response.TokenInfo;
import com.imaginecup.morpheus.utils.dto.Response;

public interface MemberService {

    TokenInfo login(String memberId, String password);
    Response join(JoinDto joinDto);
    TokenInfo reissue(ReissuedTokenDto reissuedTokenDto);
    void logout(String id);


}
