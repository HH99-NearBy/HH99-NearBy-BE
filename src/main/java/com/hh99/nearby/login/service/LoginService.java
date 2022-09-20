package com.hh99.nearby.login.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.login.dto.request.LoginRequestDto;
import com.hh99.nearby.login.dto.response.LoginResponseDto;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.repository.RefreshTokenRepository;
import com.hh99.nearby.security.jwt.TokenDto;
import com.hh99.nearby.security.jwt.TokenProvider;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final LevelCheck levelCheck;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final MemberChallengeRepository memberChallengeRepository;

    @Transactional
    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse response, HttpSession httpSession) {
        Member member = isPresentMemberByEmail(requestDto.getEmail());
        if (null == member) {
            return ResponseEntity.badRequest().body(Map.of("msg", "사용자를 찾을수 없습니다."));
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("msg", "잘못된 입력입니다."));
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

//        Long level = levelCheck.levelCheck(member.getNickname()); // 레벨 계산
        List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname()); // 레벨 계산
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .level(levelAndPoint.get(1)+ "LV")
                .build();
        httpSession.setAttribute("loggedUser", member.getNickname());
        return ResponseEntity.ok().body(Map.of("msg", "로그인 성공", "data", loginResponseDto));
    }

    public ResponseEntity<?> logout(UserDetails user, HttpSession session) {
        Member member = isPresentMemberByEmail(user.getUsername());
        if (null == member) {
            return ResponseEntity.badRequest().body(Map.of("msg", "사용자를 찾을수 없습니다."));
        }
        tokenProvider.deleteRefreshToken(member);
        session.invalidate();
        return ResponseEntity.ok().body(Map.of("msg", "로그아웃 성공"));
    }

    @Transactional(readOnly = true)
    public Member isPresentMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    }
}
