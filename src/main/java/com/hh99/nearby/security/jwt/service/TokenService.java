package com.hh99.nearby.security.jwt.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.repository.RefreshTokenRepository;
import com.hh99.nearby.security.dto.ReissueDto;
import com.hh99.nearby.security.jwt.TokenDto;
import com.hh99.nearby.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    private final TokenProvider tokenProvider;

    @Value("${jwt.secret}")
    private String secret;

    public ResponseEntity<?> reissue(ReissueDto reissueDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Member member = memberRepository.findByNickname(reissueDto.getNickname()).get();
        String refreshToken = refreshTokenRepository.findByMember(member).get().getToken();
        if (tokenProvider.validateToken(refreshToken,request)) {
            TokenDto tokenDto = tokenProvider.generateTokenDto(member);
            response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
            return ResponseEntity.ok().body(Map.of("msg", "토큰재발급 성공"));
        }
        return ResponseEntity.badRequest().body(Map.of("msg", "재로그인이 필요합니다."));
    }
}
