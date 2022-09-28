package com.hh99.nearby.mypage.service;


import com.hh99.nearby.entity.*;
import com.hh99.nearby.mypage.dto.request.MypageRequestDto;
import com.hh99.nearby.mypage.dto.response.MypageJoinList;
import com.hh99.nearby.mypage.dto.response.MypageFinishList;
import com.hh99.nearby.mypage.dto.response.MemberPageResponseDto;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.Graph;
import com.hh99.nearby.util.LevelCheck;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final LevelCheck levelCheck;

    private final MemberRepository memberRepository;

    private final ChallengeRepository challengeRepository;

    private final MemberChallengeRepository memberChallengeRepository;
    private final Graph graph;
    private final JPAQueryFactory jpaQueryFactory;


    @Transactional
    public ResponseEntity<?> memberPage(@AuthenticationPrincipal UserDetails user) {
        Member member = memberRepository.findByNickname(user.getUsername()).get(); // 맴버 불러오기

        List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname()); // 레벨 계산

        List<MemberChallenge> memberChallenge = memberChallengeRepository.findByMember(member);
        Long hour = 0L;
        Long minute = 0L;
        for (int i = 0; i < memberChallenge.size(); i++) {
            hour += memberChallenge.get(i).getRealTime();
            minute += memberChallenge.get(i).getRealTime();
        }

        return ResponseEntity.ok(MemberPageResponseDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .profileImg(member.getProfileImg())
                .level("LV."+levelAndPoint.get(1))
                .rank(member.getMyRank() + "등")
                .remainingTime((minute % 60) + "분")
                .totalTime((hour / 60) + "시간" + (minute % 60) + "분")
                .graph(member.getGraph())
                .build());
    }


    public ResponseEntity<?> joinChallege(UserDetails user, int pageNum) { //참여한 리스트
        Member member = memberRepository.findByNickname(user.getUsername()).get(); // 맴버 불러오기
        pageNum = pageNum - 1;
        int size = 4;
        Pageable pageable = PageRequest.of(pageNum,size);
        List<MemberChallenge> challengeList = joinChallenge(member,pageable);
        List<MypageJoinList> mypageJoinList = new ArrayList<>();
        for (int i=0;i<challengeList.size();i++) {
                mypageJoinList.add(
                        MypageJoinList.builder()
                                .title(challengeList.get(i).getChallenge().getTitle())
                                .challengeImg(challengeList.get(i).getChallenge().getChallengeImg())
                                .startDay(challengeList.get(i).getChallenge().getStartDay())
                                .startTime(challengeList.get(i).getChallenge().getStartTime())
                                .tagetTime(challengeList.get(i).getChallenge().getTargetTime())
                                .endTime(challengeList.get(i).getChallenge().getEndTime())
                                .limitPeople(challengeList.get(i).getChallenge().getLimitPeople())
                                .build()
                );
        }
       return ResponseEntity.ok(mypageJoinList);
    }

    public ResponseEntity<?> finishChallenge(UserDetails user, int pageNum) { //완료한 리스트
        Member member = memberRepository.findByNickname(user.getUsername()).get(); // 맴버 불러오기
        pageNum = pageNum - 1;
        int size = 5;
        Pageable pageable = PageRequest.of(pageNum,size);

        List<MemberChallenge> finishChallengeList = finishChallenge(member,pageable);
        List<MypageFinishList> finishLists = new ArrayList<>(); // 리스트 선언
        for (int i = 0; i < finishChallengeList.size(); i++) {
                finishLists.add(MypageFinishList.builder()
                        .title(finishChallengeList.get(i).getChallenge().getTitle()) //타이틀
                        .startTime(finishChallengeList.get(i).getChallenge().getStartTime()) //시작시간
                        .tagetTime(finishChallengeList.get(i).getChallenge().getTargetTime()) //타켓시간
                        .endtime(finishChallengeList.get(i).getChallenge().getEndTime()) //엔드타임임
                        .build());
        }
        return ResponseEntity.ok(finishLists);
    }


    @Transactional //수정서비스
    public ResponseEntity<?> memberUpdate(MypageRequestDto requestDto, @AuthenticationPrincipal UserDetails user) {
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());
        member.get().update(requestDto);
        return ResponseEntity.ok(Map.of("msg", "프로필 수정 완료!"));
    }


    public List<MemberChallenge> finishChallenge(Member member, Pageable pageable) {
        QMemberChallenge memberChallenge = QMemberChallenge.memberChallenge;
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory
                .selectFrom(memberChallenge)
                .where(
                        memberChallenge.member.eq(member),
                        memberChallenge.challenge.endTime.before(now)
                )
                .orderBy(memberChallenge.challenge.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<MemberChallenge> joinChallenge(Member member, Pageable pageable) {
        QMemberChallenge memberChallenge = QMemberChallenge.memberChallenge;
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory
                .selectFrom(memberChallenge)
                .where(
                        memberChallenge.member.eq(member),
                        memberChallenge.challenge.endTime.after(now)
                )
                .orderBy(memberChallenge.challenge.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }



}
