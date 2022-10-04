package com.hh99.nearby.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.net.http.HttpHeaders;

@Builder
@Getter
@AllArgsConstructor
public class KakaoResponseDto {
    private HttpHeaders headers;

    private Long kakaoId;

    private String profileImg;

}
