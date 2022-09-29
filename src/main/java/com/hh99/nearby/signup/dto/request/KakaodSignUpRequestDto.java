package com.hh99.nearby.signup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaodSignUpRequestDto {

    private Long kakaoId;

    private String nickname;

    private String profileImg;
}
