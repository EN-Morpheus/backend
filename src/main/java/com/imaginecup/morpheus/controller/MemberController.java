package com.imaginecup.morpheus.controller;

import com.imaginecup.morpheus.domain.request.member.JoinDto;
import com.imaginecup.morpheus.domain.request.member.LoginDto;
import com.imaginecup.morpheus.domain.request.token.ReissuedTokenDto;
import com.imaginecup.morpheus.domain.response.Response;
import com.imaginecup.morpheus.service.MemberService;
import com.imaginecup.morpheus.domain.response.token.TokenInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member API", description = "회원 정보 관리")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDto loginDto) {
        String memberId = loginDto.getMemberId();
        String password = loginDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);

        Map<String, Object> response = new HashMap<>();
        response.put("result", "SUCCESS");
        response.put("token", tokenInfo);

        return response;
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join(@RequestBody JoinDto joinDto) {
        Map<String, Object> response = memberService.join(joinDto);
        return new ResponseEntity(response, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "토큰 재발급", description = "AccessToken 기간이 만료되었을 경우, 재발급해 주는 코드")
    @PostMapping("/reissue")
    public ResponseEntity reissue(@RequestBody ReissuedTokenDto reissuedTokenDto) {
        try {
            TokenInfo tokenInfo = memberService.reissue(reissuedTokenDto);
            return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/duplicated-id")
    public ResponseEntity<Map<String, Object>> checkDuplication(@RequestParam("id") String id) {
        return memberService.checkDuplicatedId(id);
    }

}