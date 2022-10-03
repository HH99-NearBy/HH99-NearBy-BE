package com.hh99.nearby.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class MypageWishResponseDto {

    private int totalPage;

    private List<MypageWishList> mypageWishList;
}
