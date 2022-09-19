package com.hh99.nearby.rank.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.rank.dto.RankPageDto;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RankPageService {

    private final MemberRepository memberRepository;
    private final LevelCheck levelCheck;

    public ResponseEntity<?> getRank(@AuthenticationPrincipal UserDetails user){

//        List<Member> allByOrderByPointsDesc = memberRepository.findAllByOrderByPointsDesc();

        List<Member> allByOrderByPointsDesc= memberRepository.rank();

        List<RankPageDto> rankPageDtos = new ArrayList<>();

        for ( Member member : allByOrderByPointsDesc){
            List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname());
            rankPageDtos.add(RankPageDto.builder()
                    .id(member.getId())
                    .profileImg(member.getProfileImg())
                    .level(levelAndPoint.get(1)+"LV")
                    .nickname(member.getNickname())
                    .score(member.getPoints())
                    .build());
        }

        return ResponseEntity.ok().body(Map.of("msg","랭킹 조회 완료","data",rankPageDtos));
    }
}
