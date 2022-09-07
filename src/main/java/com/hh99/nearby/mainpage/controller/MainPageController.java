package com.hh99.nearby.mainpage.controller;

import com.hh99.nearby.mainpage.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MainPageController {

    private final MainPageService mainPageService;

    //모든 리스트 불러오는 API
    @GetMapping("/api/posts")
    public ResponseEntity<?> getAllChallenge(@RequestParam int pageNum, //페이지의 수
                                             @RequestParam int size){   //글갯수
        return mainPageService.getAllChallenge(pageNum,size);
    }

    //참가신청한 리스트 불러오는 API
    @GetMapping("/api/joinposts")
    public ResponseEntity<?> joinAllChallenge(@AuthenticationPrincipal UserDetails user){
        return mainPageService.joinAllChallenge(user);
    }
}
