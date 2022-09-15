package com.hh99.nearby.login.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Kakaocode {

   private String code;

   private String nickname;
}
