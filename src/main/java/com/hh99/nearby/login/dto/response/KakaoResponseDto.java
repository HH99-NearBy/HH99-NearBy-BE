package com.hh99.nearby.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class KakaoResponseDto {
    private Long kakaoId;
    private String profileImg;

}
