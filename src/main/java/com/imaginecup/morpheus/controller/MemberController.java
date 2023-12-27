package com.imaginecup.morpheus.controller;

import com.imaginecup.morpheus.domain.user.LoginDto;
import com.imaginecup.morpheus.service.UserService;
import com.imaginecup.morpheus.token.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final UserService userService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDto loginDto) {
        String memberId = loginDto.getMemberId();
        String password = loginDto.getPassword();
        TokenInfo tokenInfo = userService.login(memberId, password);
        return tokenInfo;
    }
}