package com.hh99.nearby.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hh99.nearby.login.dto.request.KakaocodeDto;
import com.hh99.nearby.login.dto.request.LoginRequestDto;
import com.hh99.nearby.login.dto.request.NicknameRequestDto;
import com.hh99.nearby.login.service.KakaoLoginService;
import com.hh99.nearby.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    private final KakaoLoginService kakaoLoginService;

    //로그인
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return loginService.login(requestDto, response);
    }

    //로그아웃
    @RequestMapping(value = "/api/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails user) {
        return loginService.logout(user);
    }

    @PostMapping("/api/kakaologin")
    public ResponseEntity<?> kakaoLogin(@RequestBody KakaocodeDto kakaocode, HttpServletResponse response) throws JsonProcessingException {
        return kakaoLoginService.kakaoLogin(kakaocode, response);
    }

    @PutMapping("/api/nicknameupdate")
    public ResponseEntity<?> nicknameupdate(@RequestBody NicknameRequestDto nicknameRequestDto, @AuthenticationPrincipal UserDetails user){
        return loginService.nicknameupdate(nicknameRequestDto,user);
    }
}