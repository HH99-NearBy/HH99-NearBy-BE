package com.hh99.nearby.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@Getter
public class MypageFinishList {

    private String title; //제목

    private LocalTime startTime; //시작 시간

    private Long tagetTime; // 진행시간

    private LocalDateTime endtime; // 종료시간

    private LocalDate startDay; // 시작일자
}
