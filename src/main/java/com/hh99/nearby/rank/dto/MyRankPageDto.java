package com.hh99.nearby.rank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MyRankPageDto {
    private Long id;
    private String rank;
    private String profileImg;
    private String level;
    private String nickname;
    private Long score;
    private List<Long> graph;

}
