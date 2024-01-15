package com.imaginecup.morpheus.member.service;

import com.imaginecup.morpheus.utils.SecurityUtils;
import com.imaginecup.morpheus.utils.constant.Authority;
import com.imaginecup.morpheus.member.dto.request.JoinDto;
import com.imaginecup.morpheus.utils.response.ResponseHandler;
import com.imaginecup.morpheus.utils.token.dto.request.ReissuedTokenDto;
import com.imaginecup.morpheus.utils.response.dto.DetailResponse;
import com.imaginecup.morpheus.member.domain.Member;
import com.imaginecup.morpheus.utils.token.domain.RefreshToken;
import com.imaginecup.morpheus.member.dao.MemberRepository;
import com.imaginecup.morpheus.utils.token.dao.RefreshTokenRepository;
import com.imaginecup.morpheus.utils.token.service.JwtTokenProvider;
import com.imaginecup.morpheus.utils.token.dto.response.TokenInfo;
import com.imaginecup.morpheus.utils.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder().key(authentication.getName()).value(tokenInfo.getRefreshToken()).build();

        refreshTokenRepository.save(refreshToken);

        return tokenInfo;
    }

    @Override
    public Response join(JoinDto joinDto) {
        Response response = new Response();

        Member member = Member.builder().memberId(joinDto.getId()).password(passwordEncoder.encode(joinDto.getPassword())).name(joinDto.getName()).email(joinDto.getEmail()).authority(Authority.ROLE_USER).build();

        memberRepository.save(member);

        response.of("result", "SUCCESS");
        response.of("code", DetailResponse.builder().code(204).message("회원 가입 성공").build());

        return response;
    }

    @Override
    public TokenInfo reissue(ReissuedTokenDto reissuedTokenDto) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissuedTokenDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(reissuedTokenDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName()).orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(reissuedTokenDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenInfo tokenDto = jwtTokenProvider.generateToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Override
    public void logout() {
        refreshTokenRepository.findByKey(SecurityUtils.getCurrentMemberId())
                .ifPresentOrElse(
                        refreshTokenRepository::delete,
                        () -> {
                            throw new RuntimeException("유효한 ID가 아닙니다.");
                        }
                );
    }

}