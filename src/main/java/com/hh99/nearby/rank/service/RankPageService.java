package com.hh99.nearby.rank.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.rank.dto.RankPageDto;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.Graph;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RankPageService {

    private final MemberRepository memberRepository;
    private final LevelCheck levelCheck;
    private final Graph graph;

    @Transactional
    public ResponseEntity<?> getRank(@AuthenticationPrincipal UserDetails user){

//        List<Member> allByOrderByPointsDesc = memberRepository.findAllByOrderByPointsDesc();

        List<Member> allByOrderByPointsDesc= memberRepository.rank();

        List<RankPageDto> rankPageDtos = new ArrayList<>();

        for ( Member member : allByOrderByPointsDesc){
            List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname());
            List<Long> sevengraph = graph.SevenDaysGraph(member.getNickname());
            rankPageDtos.add(RankPageDto.builder()
                    .id(member.getId())
                    .profileImg(member.getProfileImg())
                    .level(levelAndPoint.get(1)+"LV")
                    .nickname(member.getNickname())
                    .score(member.getPoints())
                    .graph(sevengraph)
                    .build());
        }
        if (user != null) {
            for (int i = 0; i < rankPageDtos.size(); i++) {
                if (user.getUsername().equals(rankPageDtos.get(i).getNickname())) {
                    System.out.println(i + 1);
                    int myRank = i+1;
                    String nickname = rankPageDtos.get(i).getNickname();
                    memberRepository.updateRank(myRank, nickname);
                    System.out.println(allByOrderByPointsDesc.get(i).getMyRank());
                }
            }
        }
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());
        System.out.println(member.get().getNickname());
        System.out.println(member.get().getMyRank());


        return ResponseEntity.ok().body(Map.of("msg","랭킹 조회 완료","data",rankPageDtos));
    }
}
