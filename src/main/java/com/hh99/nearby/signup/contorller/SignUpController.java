package com.hh99.nearby.signup.contorller;

import com.hh99.nearby.signup.dto.request.KakaodSignUpRequestDto;
import com.hh99.nearby.signup.dto.request.SignUpRequestDto;
import com.hh99.nearby.signup.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class SignUpController {

    private final SignUpService signUpService;

    @Value("${signup.redirect.url}")
    private String url;

    //회원가입
    @RequestMapping(value = "/api/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequestDto requestDto) throws MessagingException {
        return signUpService.createMember(requestDto);
    }
    //이메일 인증
    @RequestMapping(value = "/api/email",method = RequestMethod.GET)
    public ResponseEntity<?> email(@RequestParam("id") Long id,HttpServletResponse response) throws IOException {
        response.sendRedirect(url);
        return signUpService.email(id);
    }
    
    //닉네임 체크
    @PostMapping("/api/nicknamecheck")
    public ResponseEntity<?> nicknamecheck(@RequestBody SignUpRequestDto nickname){
        return signUpService.nicknamecheck(nickname);
    }
    //이메일 중복검사
    @PostMapping("/api/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody SignUpRequestDto email){
        return signUpService.emailCheck(email);
    }

    @PostMapping("/api/kakaosign")
    public ResponseEntity<?> kakaoSign(@RequestBody KakaodSignUpRequestDto kakaodSignUpRequestDto, HttpServletResponse response){
        return signUpService.kakaoSignUp(kakaodSignUpRequestDto,response);
    }
}
