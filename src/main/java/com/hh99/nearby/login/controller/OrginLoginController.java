package com.hh99.nearby.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hh99.nearby.login.dto.request.KakaoRequestDto;
import com.hh99.nearby.login.dto.request.LoginRequestDto;
import com.hh99.nearby.login.service.LoginService;
import com.hh99.nearby.login.service.KakaoLoginService;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response, HttpSession httpSession,@AuthenticationPrincipal UserDetails user) {
        return  loginService.login(requestDto, response,httpSession,user);
    }

    //로그아웃
    @RequestMapping(value = "/api/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails user, HttpServletResponse response) {
        return loginService.logout(user);
    }

    @GetMapping("/api/kakaologin")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code,HttpServletResponse response) throws JsonProcessingException {
        System.out.println("인가코드 : "+code);
        KakaoRequestDto kakaoUser = kakaoLoginService.kakaoLogin(code,response);
        return ResponseEntity.ok(kakaoUser);
    }
}
