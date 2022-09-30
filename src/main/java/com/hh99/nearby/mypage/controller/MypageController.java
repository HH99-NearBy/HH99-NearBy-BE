package com.hh99.nearby.mypage.controller;

import com.hh99.nearby.mypage.dto.request.MypageRequestDto;
import com.hh99.nearby.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;

   // 마이페이지
    @GetMapping("/myinfo")
    public ResponseEntity<?> memberPage(@AuthenticationPrincipal UserDetails user){
        return mypageService.memberPage(user);
    }

    @GetMapping("/joinchallenge")
    public ResponseEntity<?> joinChallenge(@AuthenticationPrincipal UserDetails user, @RequestParam int pageNum){
        return mypageService.joinChallege(user,pageNum);
    }

    @GetMapping("/finishchallenge")
    public ResponseEntity<?> finishChallenge(@AuthenticationPrincipal UserDetails user, @RequestParam int pageNum){
        return mypageService.finishChallenge(user,pageNum);
    }



    //프로필 수정
    @PutMapping("/member")
    public ResponseEntity<?> memberUpdate(@RequestBody MypageRequestDto requestDto, @AuthenticationPrincipal UserDetails user, HttpServletResponse response){
        return mypageService.memberUpdate(requestDto,user,response);
    }
}
