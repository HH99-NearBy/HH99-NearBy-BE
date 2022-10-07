package com.hh99.nearby.mypage.service;


import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.entity.QMemberChallenge;
import com.hh99.nearby.mypage.dto.request.MypageRequestDto;
import com.hh99.nearby.mypage.dto.response.*;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.security.dto.TokenDto;
import com.hh99.nearby.security.jwt.TokenProvider;
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

import javax.servlet.http.HttpServletResponse;
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

    private final MemberChallengeRepository memberChallengeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    private final  TokenProvider tokenProvider;


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

        String myRank = member.getMyRank() == 0 ? "---": member.getMyRank()+"등";

        MemberPageResponseDto memberPageResponseDtoresponseDto = MemberPageResponseDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .profileImg(member.getProfileImg())
                .level("LV."+levelAndPoint.get(1))
                .rank(myRank)
                .remainingTime((minute % 70))
                .totalTime((hour / 60) + "시간" + (minute % 60) + "분")
                .graph(member.getGraph())
                .build();

        return ResponseEntity.ok().body(Map.of("msg","맴버 조회완료","data",memberPageResponseDtoresponseDto));
    }


    public ResponseEntity<?> joinChallege(UserDetails user, int pageNum) { //참여한 리스트
        Member member = memberRepository.findByNickname(user.getUsername()).get(); // 맴버 불러오기
        pageNum = pageNum - 1;
        int size = 4;
        Pageable pageable = PageRequest.of(pageNum,size);
        List<MemberChallenge> challengeList = joinChallenge(member,pageable);
        List<MemberChallenge> challengeSize = joinChallenge(member);
        List<MypageJoinList> mypageJoinList = new ArrayList<>();
        for (int i=0;i<challengeList.size();i++) {
            long participatePeople = challengeList.get(i).getChallenge().getMemberChallengeList().size();
                mypageJoinList.add(
                        MypageJoinList.builder()
                                .title(challengeList.get(i).getChallenge().getTitle())
                                .challengeImg(challengeList.get(i).getChallenge().getChallengeImg())
                                .startDay(challengeList.get(i).getChallenge().getStartDay())
                                .startTime(challengeList.get(i).getChallenge().getStartTime())
                                .tagetTime(challengeList.get(i).getChallenge().getTargetTime())
                                .endTime(challengeList.get(i).getChallenge().getEndTime())
                                .limitPeople(challengeList.get(i).getChallenge().getLimitPeople())
                                .participatePeople(participatePeople)
                                .build()
                );
        }
        double totalPage =  Math.ceil((double) challengeSize.size()/(double) size);

        MypageJoinResponseDto mypageResponseDto = MypageJoinResponseDto.builder()
                .totalPage((int)totalPage)
                .mypageJoinList(mypageJoinList)
                .build();
        return ResponseEntity.ok().body(Map.of("msg","조회 완료","data",mypageResponseDto));
    }

    public ResponseEntity<?> finishChallenge(UserDetails user, int pageNum) { //완료한 리스트
        Member member = memberRepository.findByNickname(user.getUsername()).get(); // 맴버 불러오기
        pageNum = pageNum - 1;
        int size = 5;
        Pageable pageable = PageRequest.of(pageNum,size);
        List<MemberChallenge> finishChallengeList = finishChallenge(member,pageable);
        List<MemberChallenge> finishChallengesize = finishChallenge(member);
        List<MypageFinishList> finishLists = new ArrayList<>(); // 리스트 선언
        for (int i = 0; i < finishChallengeList.size(); i++) {
                finishLists.add(MypageFinishList.builder()
                        .title(finishChallengeList.get(i).getChallenge().getTitle()) //타이틀
                        .startTime(finishChallengeList.get(i).getChallenge().getStartTime()) //시작시간
                        .tagetTime(finishChallengeList.get(i).getChallenge().getTargetTime()) //타켓시간
                        .endtime(finishChallengeList.get(i).getChallenge().getEndTime()) //엔드타임임
                        .startDay(finishChallengeList.get(i).getStartDay()) //시작일자
                        .build());
        }
        double totalPage =  Math.ceil((double) finishChallengesize.size()/(double) size);

        MypageFinishResponseDto mypageFinishResponseDto = MypageFinishResponseDto.builder()
                .totalPage((int)totalPage)
                .mypageFinishLists(finishLists)
                .build();
        return ResponseEntity.ok().body(Map.of("msg","조회 완료","data",mypageFinishResponseDto));
    }


    @Transactional //수정서비스
    public ResponseEntity<?> memberUpdate(MypageRequestDto requestDto, @AuthenticationPrincipal UserDetails user, HttpServletResponse response) {
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());
        member.get().update(requestDto);
        TokenDto tokenDto = tokenProvider.generateTokenDto(member.get());
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        return ResponseEntity.ok(Map.of("msg", "프로필 수정 완료!"));
    }

    public List<MemberChallenge> finishChallenge(Member member, Pageable pageable) { // 페이징처리를 위한 메서드
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

    public List<MemberChallenge> finishChallenge(Member member) { // 사이즈를 가져오기 위한 메서드
        QMemberChallenge memberChallenge = QMemberChallenge.memberChallenge;
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory
                .selectFrom(memberChallenge)
                .where(
                        memberChallenge.member.eq(member),
                        memberChallenge.challenge.endTime.before(now)
                )
                .orderBy(memberChallenge.challenge.id.desc())
                .fetch();
    }

    public List<MemberChallenge> joinChallenge(Member member, Pageable pageable) { // 페이징처리를 위한 메서드
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

    public List<MemberChallenge> joinChallenge(Member member) { // 사이즈를 가져오기 위한 메서드
        QMemberChallenge memberChallenge = QMemberChallenge.memberChallenge;
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory
                .selectFrom(memberChallenge)
                .where(
                        memberChallenge.member.eq(member),
                        memberChallenge.challenge.endTime.after(now)
                )
                .orderBy(memberChallenge.challenge.id.desc())
                .fetch();
    }
}