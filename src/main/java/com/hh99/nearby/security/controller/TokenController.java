package com.hh99.nearby.security.controller;

import com.hh99.nearby.security.dto.ReissueDto;
import com.hh99.nearby.security.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class TokenController {

 private final TokenService tokenService;

    //토큰 재발급
    @PostMapping("/api/token")
    public ResponseEntity<?> reissueToken(@RequestBody ReissueDto reissueDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return tokenService.reissue(reissueDto,request,response);
    }

}