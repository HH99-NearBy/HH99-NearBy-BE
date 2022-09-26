package com.hh99.nearby.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Builder
@Getter
@AllArgsConstructor
public class SearchResponseDto {

    private Long id; //첼린지 아이디

    private String title; //첼린지 제목

    private String challengeImg; //첼린지이미지 url

    private LocalDate startDay; //시작 날짜

    private LocalTime startTime; //시작 시간

    private Long tagetTime; // 진행시간

    private LocalDateTime endTime; // 종료시간

    private Long limitPeople; //인원제한

    private Long participatePeople; //참여인원
}
