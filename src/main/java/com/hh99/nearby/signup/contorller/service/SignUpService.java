package com.hh99.nearby.signup.contorller.service;


import com.hh99.nearby.signup.dto.SignUpRequestDto;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.Graph;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final Graph graph;

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
        graph.SevenDaysGraph();

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
}


