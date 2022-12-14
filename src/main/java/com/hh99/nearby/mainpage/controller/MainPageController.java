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
    public ResponseEntity<?> getAllChallenge(@RequestParam long challengeId, //조회된 마지막 챌린지 아이디
                                             @RequestParam long size){   //글갯수
        return mainPageService.getAllChallenge(challengeId,size);
    }

    @GetMapping("/api/posts/recruit")
    public ResponseEntity<?> getAllRecruitChallenge(@RequestParam long challengeId, //조회된 마지막 챌린지 아이디
                                             @RequestParam long size){   //글갯수
        return mainPageService.getAllRecruitChallenge(challengeId,size);
    }

    @GetMapping("/api/posts/close")
    public ResponseEntity<?> getAllCloseChallenge(@RequestParam long challengeId, //조회된 마지막 챌린지 아이디
                                                    @RequestParam long size){   //글갯수
        return mainPageService.getAllCloseChallenge(challengeId,size);
    }


    //참가신청한 리스트 불러오는 API
    @GetMapping("/api/joinposts")
    public ResponseEntity<?> joinAllChallenge(@AuthenticationPrincipal UserDetails user){
        return mainPageService.joinAllChallenge(user);
    }
}
