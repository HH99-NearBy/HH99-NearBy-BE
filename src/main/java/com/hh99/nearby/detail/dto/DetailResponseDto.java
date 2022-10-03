package com.hh99.nearby.detail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailResponseDto {
    private String title;
    private String challengeImg;
    private LocalDate startDay;
    private LocalTime startTime;
    private Long targetTime;
    private LocalDateTime endTime;
    private Long limitPeople;
    private Long participatePeople;
    private String content;
    private String notice;
    private String writer;
    private String level;
    private List<String> challengeTag;
    private Boolean isJoin;

}
