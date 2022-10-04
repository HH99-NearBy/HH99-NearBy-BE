package com.hh99.nearby.rank.service;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.rank.dto.MyRankPageDto;
import com.hh99.nearby.rank.dto.RankPageDto;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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



    @Transactional
    public ResponseEntity<?> getRank(@AuthenticationPrincipal UserDetails user, int pageNum, int size) {
        //로그인 헀을때
        if (user != null) {
            pageNum = pageNum - 1;
            Pageable pageable = PageRequest.of(pageNum, size);
            Slice<Member> allByOrderByMyRankAsc = memberRepository.rank2(pageable);

            List<RankPageDto> rankPageDtos = new ArrayList<>();

            for (Member member : allByOrderByMyRankAsc) {
                List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname());
                rankPageDtos.add(RankPageDto.builder()
                        .id(member.getId())
                        .profileImg(member.getProfileImg())
                        .level("LV."+levelAndPoint.get(1))
                        .nickname(member.getNickname())
                        .score(member.getPoints())
                        .graph(member.getGraph())
                        .build());
            }

//            //내 등수 구해서 업데이트하기
//            List<Member> allByOrderByPointsDesc2 = memberRepository.rank();
//            for (int i = 0; i < allByOrderByPointsDesc2.size(); i++) {
//                if (user.getUsername().equals(allByOrderByPointsDesc2.get(i).getNickname())) {
//                    long myRank = (long) i + 1;
//                    String nickname = allByOrderByPointsDesc2.get(i).getNickname();
//                    memberRepository.updateRank(myRank, nickname);
//                }
//            }
            //랭킹페이지에서 내 정보 보여주기
            Optional<Member> member2 = memberRepository.findByNickname(user.getUsername());
//            List<Long> sevengraph = graph.SevenDaysGraph(member2.get().getNickname());
            List<Long> levelAndPoint = levelCheck.levelAndPoint(member2.get().getNickname());
            String myRank = member2.get().getMyRank() == 0 ? "---": member2.get().getMyRank()+"등";
            MyRankPageDto myRankPageDto = new MyRankPageDto(
                    member2.get().getId(),
                    myRank,
                    member2.get().getProfileImg(),
                    "LV."+levelAndPoint.get(1),
                    member2.get().getNickname(),
                    member2.get().getPoints(),
                    member2.get().getGraph()
            );

            return ResponseEntity.ok().body(Map.of("msg", "랭킹 조회 완료", "data", rankPageDtos, "myRank", myRankPageDto));
        }

        //로그인 안했을때
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, size);
        Slice<Member> allByOrderByMyRankAsc = memberRepository.rank2(pageable);

        List<RankPageDto> rankPageDtos = new ArrayList<>();

        for (Member member : allByOrderByMyRankAsc) {
            List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname());
            rankPageDtos.add(RankPageDto.builder()
                    .id(member.getId())
                    .profileImg(member.getProfileImg())
                    .level("LV."+levelAndPoint.get(1))
                    .nickname(member.getNickname())
                    .score(member.getPoints())
                    .graph(member.getGraph())
                    .build());
        }
        return ResponseEntity.ok().body(Map.of("msg", "랭킹 조회 완료", "data", rankPageDtos));

    }
}
