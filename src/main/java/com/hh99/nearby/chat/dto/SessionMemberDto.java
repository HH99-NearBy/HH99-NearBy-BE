package com.hh99.nearby.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class SessionMemberDto {
    private final String level;
    private final String nickname;
    private final LocalDateTime entryTime;
}
