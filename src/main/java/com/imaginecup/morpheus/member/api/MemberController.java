package com.imaginecup.morpheus.member.api;

import com.imaginecup.morpheus.member.dto.request.JoinDto;
import com.imaginecup.morpheus.member.dto.request.LoginDto;
import com.imaginecup.morpheus.member.service.MemberService;
import com.imaginecup.morpheus.token.dto.request.ReissuedTokenDto;
import com.imaginecup.morpheus.member.service.MemberServiceImpl;
import com.imaginecup.morpheus.token.dto.response.TokenInfo;
import com.imaginecup.morpheus.utils.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member API", description = "회원 정보 관리")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public Response login(@RequestBody LoginDto loginDto) {
        String memberId = loginDto.getMemberId();
        String password = loginDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);

        Response response = new Response();
        response.of("result", "SUCCESS");
        response.of("token", tokenInfo);

        return response;
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/join")
    public ResponseEntity<Response> join(@RequestBody JoinDto joinDto) {
        Response response = memberService.join(joinDto);
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

    @Operation(summary = "아이디 중복 확인")
    @GetMapping("/duplicated-id")
    public ResponseEntity<Response> checkDuplication(@RequestParam("id") String id) {
        return memberService.checkDuplicatedId(id);
    }

}