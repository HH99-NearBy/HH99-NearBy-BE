package com.hh99.nearby.mypage.service;


import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.mypage.dto.request.MypageRequestDto;
import com.hh99.nearby.mypage.dto.response.MypageChallengeList;
import com.hh99.nearby.mypage.dto.response.MypageFinishList;
import com.hh99.nearby.mypage.dto.response.MypageResponseDto;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.security.jwt.TokenProvider;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class MypageService {

    private final LevelCheck levelCheck;

    private final MemberRepository memberRepository;

    private final ChallengeRepository challengeRepository;

    private final MemberChallengeRepository memberChallengeRepository;



    @Transactional
    public ResponseEntity<?> myPage(@AuthenticationPrincipal UserDetails user) {
        Member member = memberRepository.findByNickname(user.getUsername()).get(); // 맴버 불러오기

        //참가한 리스트 불러오는
        List<MemberChallenge> challengeList = memberChallengeRepository.findByMember(member);
        ArrayList<MypageChallengeList> mypageChallengeList = new ArrayList<>();
        for (MemberChallenge challenge : challengeList) {
            mypageChallengeList.add(
                    MypageChallengeList.builder()
                            .title(challenge.getChallenge().getTitle())
                            .challengeImg(challenge.getChallenge().getChallengeImg())
                            .startDay(challenge.getChallenge().getStartDay())
                            .startTime(challenge.getChallenge().getStartTime())
                            .tagetTime(challenge.getChallenge().getTargetTime())
                            .endTime(challenge.getChallenge().getEndTime())
                            .limitPeople(challenge.getChallenge().getLimitPeople())
                            .build()
            );
        }


        //내가 완료한 페이지
        LocalDateTime now = LocalDateTime.now(); //현재 시간
        List<MemberChallenge> finishChallengeList = memberChallengeRepository.findByMember(member); //참여한 첼린지
        ArrayList<MypageFinishList> finishLists = new ArrayList<>(); // 리스트 선언
        System.out.println(finishChallengeList.size());
       for (int i=0;i<finishChallengeList.size();i++){
           if(finishChallengeList.get(i).getChallenge().getEndTime().isBefore(now)){ //엔드타임이 현재 시간보다 과거일때 true
               finishLists.add(MypageFinishList.builder()
                       .title(finishChallengeList.get(i).getChallenge().getTitle()) //타이틀
                       .startTime(finishChallengeList.get(i).getChallenge().getStartTime()) //시작시간
                       .tagetTime(finishChallengeList.get(i).getChallenge().getTargetTime()) //타켓시간
                       .endtime(finishChallengeList.get(i).getChallenge().getEndTime()) //엔드타임임
                       .build());
           }
       }



        List<Long> levelAndPoint = levelCheck.levelAndPoint(member.getNickname()); // 레벨 계산

        List<MemberChallenge> memberChallenge = memberChallengeRepository.findByMember(member);
        Long hour=0L;
        Long minute=0L;
        for (int i=0;i<memberChallenge.size();i++){
            hour += memberChallenge.get(i).getRealTime();
            minute += memberChallenge.get(i).getRealTime();
        }


        return ResponseEntity.ok(MypageResponseDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .profileImg(member.getProfileImg())
                .level(levelAndPoint.get(1) + "Lv")
                .rank(member.getMyRank()+"등")
                .remainingTime((minute%60)+"분")
                .totalTime((hour/60)+"시간"+(minute%60)+"분")
                .challengeLists(mypageChallengeList)
                .finishLists(finishLists)
                .build());
    }

    @Transactional //수정서비스
    public ResponseEntity<?> memberUpdate(MypageRequestDto requestDto, @AuthenticationPrincipal UserDetails user) {
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());
        member.get().update(requestDto);
        return ResponseEntity.ok(Map.of("msg","프로필 수정 완료!"));
    }



}
