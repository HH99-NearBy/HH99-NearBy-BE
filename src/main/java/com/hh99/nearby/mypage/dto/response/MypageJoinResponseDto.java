package com.hh99.nearby.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class MypageJoinResponseDto {

    private int totalPage;

    private List<MypageJoinList> mypageJoinList;
}
