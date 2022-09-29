package com.hh99.nearby.detail.service;

import com.hh99.nearby.detail.dto.DetailResponseDto;
import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DetailService {

    private final LevelCheck levelCheck;

    private final ChallengeRepository challengeRepository;
    private final MemberRepository memberRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    @Transactional
    public ResponseEntity<?> detailModal(@PathVariable Long id) {
        Challenge challenge = isPresentChallenge(id);
        if (challenge == null) {
            return ResponseEntity.badRequest().body(Map.of("msg", "잘못된 챌린지 번호"));
        }

        long participatePeople = challenge.getMemberChallengeList().size();

        List<Long> levelAndPoint = levelCheck.levelAndPoint(challenge.getWriter().getNickname()); // 레벨 계산

        DetailResponseDto detailResponseDto = DetailResponseDto.builder()
                .title(challenge.getTitle())
                .challengeImg(challenge.getChallengeImg())
                .startDay(challenge.getStartDay())
                .startTime(challenge.getStartTime())
                .targetTime(challenge.getTargetTime())
                .endTime(challenge.getEndTime())
                .limitPeople(challenge.getLimitPeople())
                .participatePeople(participatePeople)
                .content(challenge.getContent())
                .notice(challenge.getNotice())
                .writer(challenge.getWriter().getNickname())
                .level("LV."+levelAndPoint.get(1))
                .challengeTag(challenge.getChallengeTag())
                .build();
        return ResponseEntity.ok().body(Map.of("detailModal", detailResponseDto, "msg", "상세모달 조회 완료"));
    }

    @Transactional(readOnly = true)
    public Challenge isPresentChallenge(Long id) {
        Optional<Challenge> optionalChallenge = challengeRepository.findById(id);
        return optionalChallenge.orElse(null);
    }

    //참여하기
    @Transactional
    public ResponseEntity<?> participateChallenge(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {

        Challenge challenge = isPresentChallenge(id);
        if (challenge == null) {
            return ResponseEntity.badRequest().body(Map.of("msg", "잘못된 챌린지 번호"));
        }
        Long participatePeople = memberChallengeRepository.countAllByChallenge(challenge);
        if(participatePeople>=challenge.getLimitPeople()){
            return ResponseEntity.badRequest().body(Map.of("msg", "더이상 신청할 수 없는 챌린지 입니다."));
        }
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());

        if (memberChallengeRepository.findByMember_IdAndChallenge_Id(member.get().getId(),id).isEmpty()){
        MemberChallenge memberChallenge = MemberChallenge.builder()
                .challenge(challenge)
                .member(member.get())
                .realTime(0L)
                .startDay(challenge.getStartDay())
                .build();
        memberChallengeRepository.save(memberChallenge);
            return ResponseEntity.ok().body(Map.of("msg", "참여하기 완료"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("msg", "이미 참여한 챌린지입니다."));
        }
    }
    //참여 취소하기
    @Transactional
    public ResponseEntity<?> cancelChallenge(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        Challenge challenge = isPresentChallenge(id);
        if (challenge == null) {
            return ResponseEntity.badRequest().body(Map.of("msg", "잘못된 챌린지 번호"));
        }
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());

        if (memberChallengeRepository.findByMember_IdAndChallenge_Id(member.get().getId(),id).isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("msg","참여하지 않으셨습니다."));
        }
        Optional<MemberChallenge> memberChallenge = memberChallengeRepository.findByMember_IdAndChallenge_Id(member.get().getId(),id);
        memberChallengeRepository.delete(memberChallenge.get());
        return ResponseEntity.ok().body(Map.of("msg", "참여하기 취소 완료"));
    }
}

