package com.hh99.nearby.detail.controller;

import com.hh99.nearby.detail.service.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DetailController {

    public final DetailService detailService;

    //상세 모달
    @GetMapping("/api/challenge/{id}")
    public ResponseEntity<?> detailModal(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        return detailService.detailModal(id,user);
    }

    // 참여하기
    @PostMapping("/api/challenge/ok/{id}")
    public ResponseEntity<?> participateChallenge(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        return detailService.participateChallenge(id,user);
    }
    // 참여 취소하기
    @PostMapping("/api/challenge/cancel/{id}")
    public ResponseEntity<?> cancelChallenge(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        return detailService.cancelChallenge(id,user);
    }


}
