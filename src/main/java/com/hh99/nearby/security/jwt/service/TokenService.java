package com.hh99.nearby.security.jwt.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.exception.ErrorCode;
import com.hh99.nearby.exception.PrivateException;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.repository.RefreshTokenRepository;
import com.hh99.nearby.security.dto.ReissueDto;
import com.hh99.nearby.security.dto.TokenDto;
import com.hh99.nearby.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
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

    public ResponseEntity<?> reissue(ReissueDto reissueDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Member member = memberRepository.findByNickname(reissueDto.getNickname()).get();
        String refreshToken = refreshTokenRepository.findByMember(member).get().getToken();
        if (tokenProvider.validateToken(refreshToken,request)) {
            TokenDto tokenDto = tokenProvider.generateTokenDto(member);
            response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
            return ResponseEntity.ok().body(Map.of("msg", "토큰재발급 성공"));
        }
        throw new PrivateException(ErrorCode.TOKEN_REISSUE);
    }
}
