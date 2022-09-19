package com.hh99.nearby.util;


import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LevelCheck {

    private final MemberChallengeRepository memberChallengeRepository;
    private final MemberRepository memberRepository;

    public List<Long> levelAndPoint(String nickname){ // 0번이 포인트  1번이 레벨
        List<Long> levelandpoint = new ArrayList<>();
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findAllByMember_nickname(nickname);
        System.out.println("사이즈 보기 : "+ memberChallenges.size());
        Long level = 0L;
        Long point = 0L;
        for(int i = 0;i<memberChallenges.size();i++){
            level += memberChallenges.get(i).getRealTime()/70;
            point += memberChallenges.get(i).getRealTime()*10;
        }
        levelandpoint.add(point);
        levelandpoint.add(level);


        Optional<Member> member = memberRepository.findByNickname(nickname);

        if (member.get().getPoints() != null) { // 리얼타임이 0보다 크다면
            Long points = member.get().getPoints(); // 데이터베이스 안에 리얼타임을 가져와서
            member.get().update(points + point); //현재 계산된 리얼타임과 db에 저장된 realtime을 더해서 업데이트
        } else {
            member.get().update(point); // 아니면 리얼타임 업데이트
        }

        return levelandpoint;
    }


}
