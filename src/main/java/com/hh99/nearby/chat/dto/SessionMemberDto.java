package com.hh99.nearby.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SessionMemberDto {
    private final String level;
    private final String nickname;
    private final Long entryTime;
}
