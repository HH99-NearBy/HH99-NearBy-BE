package com.hh99.nearby.mainpage.service;

import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.entity.QChallenge;
import com.hh99.nearby.mainpage.dto.MainPageResponseDto;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private final MemberRepository memberRepository;

    private final MemberChallengeRepository memberChallengeRepository;

    private final ChallengeRepository challengeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public ResponseEntity<?> getAllChallenge(long challengeId, long size) {
        List<MainPageResponseDto> allchallengelist = new ArrayList<>();
        List<Challenge> challenges = ChallengeList(challengeId, size);
        for (Challenge challenge : challenges) {

            long participatePeople = challenge.getMemberChallengeList().size();

            allchallengelist.add(
                    MainPageResponseDto.builder()
                            .id(challenge.getId())
                            .title(challenge.getTitle())
                            .challengeImg(challenge.getChallengeImg())
                            .startDay(challenge.getStartDay())
                            .startTime(challenge.getStartTime())
                            .tagetTime(challenge.getTargetTime())
                            .endTime(challenge.getEndTime())
                            .limitPeople(challenge.getLimitPeople())
                            .participatePeople(participatePeople)
                            .build()
            );

        }

        return ResponseEntity.ok(allchallengelist);
    }

    public ResponseEntity<?> getAllRecruitChallenge(long challengeId, long size){
        List<MainPageResponseDto> allchallengelist = new ArrayList<>();
        List<Challenge> challenges = RecruitChallengeList(challengeId, size);
        for (Challenge challenge : challenges) {

            long participatePeople = challenge.getMemberChallengeList().size();

            allchallengelist.add(
                    MainPageResponseDto.builder()
                            .id(challenge.getId())
                            .title(challenge.getTitle())
                            .challengeImg(challenge.getChallengeImg())
                            .startDay(challenge.getStartDay())
                            .startTime(challenge.getStartTime())
                            .tagetTime(challenge.getTargetTime())
                            .endTime(challenge.getEndTime())
                            .limitPeople(challenge.getLimitPeople())
                            .participatePeople(participatePeople)
                            .build()
            );

        }
        return ResponseEntity.ok().body(Map.of("msg","조회 완료","data",allchallengelist));
    }


    public ResponseEntity<?> joinAllChallenge(UserDetails user) {
        Member member = memberRepository.findByNickname(user.getUsername()).get();
        //참가한 리스트 불러오는
        List<MemberChallenge> challengeList = memberChallengeRepository.findByMember(member);
        ArrayList<MainPageResponseDto> mypageChallengeList = new ArrayList<>();
        for (MemberChallenge challenge : challengeList) {
            mypageChallengeList.add(
                    MainPageResponseDto.builder()
                            .id(challenge.getChallenge().getId())
                            .title(challenge.getChallenge().getTitle())
                            .challengeImg(challenge.getChallenge().getChallengeImg())
                            .startDay(challenge.getChallenge().getStartDay())
                            .startTime(challenge.getChallenge().getStartTime())
                            .tagetTime(challenge.getChallenge().getTargetTime())
                            .endTime(challenge.getChallenge().getEndTime())
                            .limitPeople(challenge.getChallenge().getLimitPeople())
                            .participatePeople((long)challenge.getChallenge().getMemberChallengeList().size())
                            .build()
            );
        }
        return ResponseEntity.ok(mypageChallengeList);
    }

    public List<Challenge> ChallengeList(Long id,Long limit) {
        QChallenge challenge = QChallenge.challenge;

        return jpaQueryFactory
                .selectFrom(challenge)
                .where(
                        id == 0 ? null : challenge.id.lt(id)
                )
                .orderBy(challenge.id.desc())
                .limit(limit)
                .fetch();
    }

    public List<Challenge> RecruitChallengeList(Long id,Long limit){
        QChallenge challenge = QChallenge.challenge;
        return jpaQueryFactory
                .selectFrom(challenge)
                .where(
                        id == 0 ? null : challenge.id.lt(id),
                        challenge.startDay.goe(LocalDate.now()),
                        challenge.startTime.goe(LocalTime.now())
                )
                .orderBy(challenge.id.desc())
                .limit(limit)
                .fetch();
    }
}
