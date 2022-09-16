package com.hh99.nearby.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SessionRequestDto {
    private final String sessionName;
    private final String token;
}
