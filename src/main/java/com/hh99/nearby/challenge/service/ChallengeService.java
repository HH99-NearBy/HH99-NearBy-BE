package com.hh99.nearby.challenge.service;

import com.hh99.nearby.challenge.dto.ChallengeRequestDto;
import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public ResponseEntity<?> createChallenge(ChallengeRequestDto challengeRequestDto, UserDetails user) throws ParseException {
        Member member = memberRepository.findByNickname(user.getUsername()).get();

        LocalDate localDate = LocalDate.parse(challengeRequestDto.getStartDay());   //챌리지 시작일자
        LocalTime localTime = LocalTime.parse(challengeRequestDto.getStartTime());   //챌리지 시작시간

        //하루나 년도가 넘어가는 상황이 발생할수 있으므로 LocalDateTime객체로 만든후에 진행시간을 더해주는 방식으로 구현
        LocalDateTime localDateTime = localDate.atTime(localTime);
        localDateTime = localDateTime.plusMinutes(challengeRequestDto.getTargetTime());

        LocalDateTime endTime = localDateTime;  //챌리지 종료시간

        String defaultImg = "https://user-images.githubusercontent.com/74406343/188258363-9a049b49-eba3-4518-9674-391d7887c5f8.png";

        challengeRepository.save(
                Challenge.builder()
                        .title(challengeRequestDto.getTitle())
                        .challengeImg(challengeRequestDto.getChallengeImg().equals("") ? "defaultImg" :challengeRequestDto.getChallengeImg())
                        .startDay(localDate)
                        .startTime(localTime)
                        .targetTime(challengeRequestDto.getTargetTime())
                        .endTime(endTime)
                        .limitPeople(challengeRequestDto.getLimit())
                        .content(challengeRequestDto.getContent())
                        .notice(challengeRequestDto.getNotice())
                        .writer(member)
                        .challengeTag(challengeRequestDto.getChallengeTag())
                        .build()
        );
        return ResponseEntity.ok().body(Map.of("msg","챌린지 작성이 완료되었습니다."));
    }

    @Transactional
    public ResponseEntity<?> updateChallenge(Long challenge_id, ChallengeRequestDto challengeRequestDto, UserDetails user) {
        Member member = memberRepository.findByNickname(user.getUsername()).get();
        Optional<Challenge> challenge = challengeRepository.findById(challenge_id);

        if(!challenge.isPresent()){
            return ResponseEntity.badRequest().body(Map.of("msg","찾을수 없는 챌린지 입니다."));
        }
        if(!challenge.get().getWriter().getNickname().equals(member.getNickname())){
            return ResponseEntity.badRequest().body(Map.of("msg","수정권한이 없는 사용자 입니다."));
        }
        challenge.get().update(challengeRequestDto);
        return ResponseEntity.ok().body(Map.of("msg","챌린지 수정이 완료되었습니다."));
    }

    public ResponseEntity<?> deleteChallenge(Long challenge_id, UserDetails user) {
        Member member = memberRepository.findByNickname(user.getUsername()).get();
        Optional<Challenge> challenge = challengeRepository.findById(challenge_id);

        if(!challenge.isPresent()){
            return ResponseEntity.badRequest().body(Map.of("msg","찾을수 없는 챌린지 입니다."));
        }
        if(!challenge.get().getWriter().getNickname().equals(member.getNickname())){
            return ResponseEntity.badRequest().body(Map.of("msg","삭제권한이 없는 사용자 입니다."));
        }
        challengeRepository.delete(challenge.get());
        return ResponseEntity.ok().body(Map.of("msg","챌린지 삭제가 완료되었습니다."));
    }
}
