package com.hh99.nearby.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hh99.nearby.login.dto.request.Kakaocode;
import com.hh99.nearby.login.dto.request.LoginRequestDto;
import com.hh99.nearby.login.service.KakaoLoginService;
import com.hh99.nearby.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class OrginLoginController {

    private final LoginService loginService;
    private final KakaoLoginService kakaoLoginService;

    //로그인
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response, HttpSession httpSession) {
        return  loginService.login(requestDto, response,httpSession);
    }

    //로그아웃
    @RequestMapping(value = "/api/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails user, HttpSession httpSession) {
        return loginService.logout(user,httpSession);
    }

    @PostMapping("/api/kakaologin")
    public ResponseEntity<?> kakaoLogin(@RequestBody Kakaocode kakaocode, HttpServletResponse response) throws JsonProcessingException {
        System.out.println("인가코드 : "+kakaocode.getCode());
        System.out.println("닉네임"+ kakaocode.getNickname());

//        KakaoRequestDto kakaoUser = kakaoLoginService.kakaoLogin(code,response);
        return kakaoLoginService.kakaoLogin(kakaocode, response);
    }
}
