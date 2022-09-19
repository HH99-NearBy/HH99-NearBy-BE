package com.hh99.nearby.rank.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RankPageService {

    private final MemberRepository memberRepository;
    private final LevelCheck levelCheck;

    public ResponseEntity<?> getRank(@AuthenticationPrincipal UserDetails user){

        return ResponseEntity.ok().body(Map.of("msg","랭킹 조회 완료","data",""));
    }
}
