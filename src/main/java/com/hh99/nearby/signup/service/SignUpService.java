package com.hh99.nearby.signup.service;


import com.hh99.nearby.entity.Member;
import com.hh99.nearby.exception.ErrorCode;
import com.hh99.nearby.exception.PrivateException;
import com.hh99.nearby.login.dto.response.LoginResponseDto;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.security.UserDetailsImpl;
import com.hh99.nearby.security.dto.TokenDto;
import com.hh99.nearby.security.jwt.TokenProvider;
import com.hh99.nearby.signup.dto.request.KakaodSignUpRequestDto;
import com.hh99.nearby.signup.dto.request.SignUpRequestDto;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final LevelCheck levelCheck;
    private final TokenProvider tokenProvider;

    //회원가입
    @Transactional
    public ResponseEntity<?> createMember(SignUpRequestDto requestDto) throws MessagingException {
        //email check
        if (null != isPresentMemberByEmail(requestDto.getEmail())) {
            throw new PrivateException(ErrorCode.SIGNUP_ALREADY_EMAIL);
        }
        //nickname check
        if (null != isPresentMemberByNickname(requestDto.getNickname())) {
            throw new PrivateException(ErrorCode.SIGNUP_ALREADY_NICKNAME);
        }
        if (requestDto.getEmail().isBlank()) {
            throw new PrivateException(ErrorCode.SIGNUP_EMPTY_EMAIL);
        }
        if (requestDto.getPassword().isBlank()) {
            throw new PrivateException(ErrorCode.SIGNUP_EMPTY_PASSWORD);
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .emailCheck(false)
                .profileImg(requestDto.getProfileImg())
                .build();

        memberRepository.save(member);
        emailService.sendSimpleMessage(requestDto.getEmail(), member.getId());

        List<Long> graph = new ArrayList<>();
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);

        member.update(graph);

        return ResponseEntity.ok().body(Map.of("msg", "회원가입 완료"));
    }

    @Transactional(readOnly = true)
    public Member isPresentMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentMemberByNickname(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    //이메일 인증
    @Transactional
    public ResponseEntity<?> email(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        member.get().update();
        return ResponseEntity.ok().body(Map.of("msg", "Email Check Success"));
    }

    
    //닉네임 중복체크
    @Transactional
    public ResponseEntity<?> nicknamecheck(SignUpRequestDto nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname.getNickname());
        if (member.isPresent()){
            throw new PrivateException(ErrorCode.SIGNUP_NICKNAME_CHECK);
        }
        return ResponseEntity.ok().body(Map.of("msg", "가입가능한 닉네임입니다."));
    }
    //이메일 중복검사
    @Transactional
    public ResponseEntity<?> emailCheck(SignUpRequestDto email) {
        Optional<Member> member = memberRepository.findByEmailAndEmailNotNull(email.getEmail());
        if (member.isPresent()){
            throw new PrivateException(ErrorCode.SIGNUP_EMAIL_CHECK);
        }
        return ResponseEntity.ok().body(Map.of("msg", "가입가능한 이메일입니다."));
    }

    public ResponseEntity<?> kakaoSignUp(KakaodSignUpRequestDto kakaodSignUpRequestDto, HttpServletResponse response) {
        Member kakaouser = Member.builder()
                .nickname(kakaodSignUpRequestDto.getNickname())
                .emailCheck(true)
                .kakaoId(kakaodSignUpRequestDto.getKakaoId())
                .profileImg(kakaodSignUpRequestDto.getProfileImg())
                .build();
        memberRepository.save(kakaouser);

        List<Long> graph = new ArrayList<>();
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);
        graph.add(0L);

        kakaouser.update(graph);

        Authentication authentication = forceLogin(kakaouser);
        kakaoUsersAuthorizationInput(kakaouser, authentication, response);
        List<Long> levelAndPoint = levelCheck.levelAndPoint(kakaouser.getNickname()); // 레벨 계산
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .profileImg(kakaouser.getProfileImg())
                .nickname(kakaouser.getNickname())
                .level("LV."+levelAndPoint.get(1))
                .remainingTime((levelAndPoint.get(0)/10 % 70))
                .totalTime(levelAndPoint.get(0)/10+"분")
                .build();
        return ResponseEntity.ok().body(Map.of("msg","로그인성공","data",loginResponseDto)); //로그인처리
    }

    // 4. 강제 로그인 처리
    private Authentication forceLogin(Member kakaoUser) {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // 5. response Header에 JWT 토큰 추가
    private void kakaoUsersAuthorizationInput(Member kakaoUser, Authentication authentication, HttpServletResponse response) {
        // response header에 token 추가
        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        TokenDto token = tokenProvider.generateTokenDto(kakaoUser);//.generateJwtToken(userDetailsImpl);
        response.addHeader("Authorization", "Bearer" + " " + token.getAccessToken());
    }
}


