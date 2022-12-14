package com.hh99.nearby.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class MemberPageResponseDto {

    //마이페이지 상단
    private String nickname; //닉네임

    private String email; //이메일

    private String profileImg; //프로필 이미지

    private String level; //레벨

    private String rank; //등수

    private Long remainingTime; //남은시간

    private String totalTime; //사용시간

    private List<Long> graph; // 일주일 그래프

}
