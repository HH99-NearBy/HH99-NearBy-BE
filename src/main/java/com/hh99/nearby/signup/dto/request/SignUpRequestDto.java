package com.hh99.nearby.signup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    private String email;
    private String nickname;
    private String password;
    private String profileImg;

}
