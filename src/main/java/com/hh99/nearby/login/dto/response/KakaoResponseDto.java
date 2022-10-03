package com.hh99.nearby.login.dto.response;

import com.hh99.nearby.login.dto.request.updateNicknameDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.http.HttpHeaders;

@Builder
@Getter
@AllArgsConstructor
public class KakaoResponseDto {
    private HttpHeaders headers;

    private Long kakaoId;

    private String profileImg;

}
