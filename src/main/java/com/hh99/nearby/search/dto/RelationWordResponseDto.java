package com.hh99.nearby.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RelationWordResponseDto {

    private Long challengeId;

    private String title; //제목
}
