package com.hh99.nearby.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ChallengeRequestDto {
    private String title; //챌리지 제목
    private String challengeImg; //챌리지 대표 이미지
    private String startDay; //챌리지 시작 일자
    private String startTime; //챌리지 시작 시간
    private Long targetTime; //챌린지 진행시간
    private Long limit; //챌린지 제한인원
    private String content; //챌린지 내용
    private String notice; //챌린지 공지사항
    private List<String> challengeTag; //챌린지에 사용된 태그리스트
}
