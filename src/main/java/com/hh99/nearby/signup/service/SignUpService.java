package com.hh99.nearby.signup.service;


import com.hh99.nearby.entity.Member;
import com.hh99.nearby.login.dto.response.LoginResponseDto;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.security.UserDetailsImpl;
import com.hh99.nearby.security.jwt.TokenDto;
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
            return ResponseEntity.badRequest().body(Map.of("msg", "Already existing email."));
        }
        //nickname check
        if (null != isPresentMemberByNickname(requestDto.getNickname())) {
            return ResponseEntity.badRequest().body(Map.of("msg", "Already existing nickname."));
        }
        if (requestDto.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("msg", "Please write proper email address to email field."));
        }
        if (requestDto.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("msg", "Please write proper password to Password field."));
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
//                .password(requestDto.getPassword())
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

        return ResponseEntity.ok().body(Map.of("msg", "Successfully sign up."));
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
            return ResponseEntity.badRequest().body(Map.of("msg", "닉네임 중복입니다"));
        }
        return ResponseEntity.ok().body(Map.of("msg", "가입가능한 닉네임입니다."));
    }
    //이메일 중복검사
    @Transactional
    public ResponseEntity<?> emailCheck(SignUpRequestDto email) {
        Optional<Member> member = memberRepository.findByEmailAndEmailNotNull(email.getEmail());
        if (member.isPresent()){
            return ResponseEntity.badRequest().body(Map.of("msg", "이메일 중복입니다."));
        }
        return ResponseEntity.ok().body(Map.of("msg", "가입가능한 이메일입니다."));
    }



    public ResponseEntity<?> kakaoSignUp(KakaodSignUpRequestDto kakaodSignUpRequestDto, HttpServletResponse response) {

        Optional<Member> member = memberRepository.findByKakaoId(kakaodSignUpRequestDto.getKakaoId()); //카카오 아이디로 맴버찾기
        member.get().update(kakaodSignUpRequestDto.getNickname()); //닉네임 업데이트
        memberRepository.save(member.get());
        Member kakaoUser = Member.builder()
                .kakaoId(member.get().getKakaoId())
                .nickname(member.get().getNickname())
                .profileImg(member.get().getProfileImg())
                .build();
        Authentication authentication = forceLogin(member.get());
        // 5. response Header에 JWT 토큰 추가
        kakaoUsersAuthorizationInput(member.get(), authentication, response);
        List<Long> levelAndPoint = levelCheck.levelAndPoint(kakaoUser.getNickname()); // 레벨 계산
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .profileImg(kakaoUser.getProfileImg())
                .nickname(kakaoUser.getNickname())
                .level("LV."+levelAndPoint.get(1))
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
        System.out.println(token.getAccessToken());
        response.addHeader("Authorization", "Bearer" + " " + token.getAccessToken());
    }


}


