package com.hh99.nearby.rank.controller;

import com.hh99.nearby.rank.service.RankPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RankController {

    private final RankPageService rankPageService;

    @GetMapping("/api/rank")
    public ResponseEntity<?> getRank(@AuthenticationPrincipal UserDetails user,int pageNum,int size){
        return rankPageService.getRank(user,pageNum,size);
    }
}
