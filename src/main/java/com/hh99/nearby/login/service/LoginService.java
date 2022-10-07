package com.hh99.nearby.login.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.exception.ErrorCode;
import com.hh99.nearby.exception.PrivateException;
import com.hh99.nearby.login.dto.request.LoginRequestDto;
import com.hh99.nearby.login.dto.request.NicknameRequestDto;
import com.hh99.nearby.login.dto.response.LoginResponseDto;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.security.dto.TokenDto;
import com.hh99.nearby.security.jwt.TokenProvider;
import com.hh99.nearby.util.LevelCheck;
import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
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

    @Transactional
    @SentrySpan
    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentMemberByEmail(requestDto.getEmail());
        if (null == member) {
            throw new PrivateException(ErrorCode.LOGIN_NOTFOUND_MEMBER);
        }
        if (!member.isEmailCheck()){
            throw new PrivateException(ErrorCode.LOGIN_NOT_CERTIFICATION);
        }
        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            throw new PrivateException(ErrorCode.LOGIN_WRONG_INPUT);
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname()); // 레벨 계산
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .level("LV."+levelAndPoint.get(1))
                .remainingTime((levelAndPoint.get(0)/10 % 70))
                .totalTime(levelAndPoint.get(0)/10+"분")
                .build();
        return ResponseEntity.ok().body(Map.of("msg", "로그인 성공", "data", loginResponseDto));
    }

    public ResponseEntity<?> logout(UserDetails user) {
        Member member = memberRepository.findByNickname(user.getUsername()).get();
        tokenProvider.deleteRefreshToken(member);
        return ResponseEntity.ok().body(Map.of("msg", "로그아웃 성공"));
    }
    @Transactional
    public ResponseEntity<?> nicknameupdate(NicknameRequestDto nicknameRequestDto, UserDetails user) {
        Member member = memberRepository.findByNickname(user.getUsername()).get();
        member.update(nicknameRequestDto.getNickname());
        return ResponseEntity.ok().body(Map.of("msg", "닉네임 업데이트 성공"));
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
