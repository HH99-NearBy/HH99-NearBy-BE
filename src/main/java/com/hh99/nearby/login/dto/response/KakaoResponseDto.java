package com.hh99.nearby.login.dto.response;

import com.hh99.nearby.login.dto.request.updateNicknameDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.http.HttpHeaders;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoResponseDto {
    private HttpHeaders headers;
    private KakaoResponseDto kakaoRequestDto;
}
