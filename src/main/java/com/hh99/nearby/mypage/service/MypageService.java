package com.hh99.nearby.mypage.service;


import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.mypage.dto.request.MypageRequestDto;
import com.hh99.nearby.mypage.dto.response.MypageChallengeList;
import com.hh99.nearby.mypage.dto.response.MypageResponseDto;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MypageService {

    private final MemberRepository memberRepository;

    private final ChallengeRepository challengeRepository;

    private final MemberChallengeRepository memberChallengeRepository;


    @Transactional
    public ResponseEntity<?> myPage(@AuthenticationPrincipal UserDetails user) {
        Member member = memberRepository.findByEmail(user.getUsername()).get();

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
        
        //내가 저장한 페이지
        
        //내가 완료한 페이지
        

        return ResponseEntity.ok(MypageResponseDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .profileImg(member.getProfileImg())
                .level(100)
                .rank(100)
                .totalTime("시간")
                .challengeLists(mypageChallengeList)
//                .finishLists()
                .build());
    }

    @Transactional //수정서비스
    public ResponseEntity<?> memberUpdate(MypageRequestDto requestDto, @AuthenticationPrincipal UserDetails user) {
        Optional<Member> member = memberRepository.findByEmail(user.getUsername());
        member.get().update(requestDto);
        return ResponseEntity.ok(Map.of("msg","수정완료"));
    }



}
