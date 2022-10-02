package com.hh99.nearby.wishlist.controller;

import com.hh99.nearby.wishlist.dto.WishListRequestDto;
import com.hh99.nearby.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/api/challenge/list/{id}")
    public ResponseEntity<?> createWishList(@PathVariable Long id,
                                            @AuthenticationPrincipal UserDetails user){
        return wishListService.createWishList(id,user);
    }

    @DeleteMapping("/api/challenge/delist/{id}")
    public ResponseEntity<?> deleteWishList(@PathVariable Long id,
                                            @AuthenticationPrincipal UserDetails user){
        return wishListService.deleteWishList(id,user);
    }
}
