package com.hh99.nearby.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String profileImg;
    private String level;
    private String nickname;
    private String remainingTime;
    private String totalTime;
}
