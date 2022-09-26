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
//    private final MemberRepository memberRepository;

    public List<Long> levelAndPoint(String nickname){ // 0번이 포인트  1번이 레벨
        List<Long> levelandpoint = new ArrayList<>();
        List<MemberChallenge> memberChallenges = memberChallengeRepository.findAllByMember_nickname(nickname);
        Long level2 = 0L;
        Long point = 0L;
        for(int i = 0;i<memberChallenges.size();i++){
            level2 += memberChallenges.get(i).getRealTime();
            point += memberChallenges.get(i).getRealTime()*10;
        }
        Long level = level2/70;
        levelandpoint.add(point);
        levelandpoint.add(level);

//        Optional<Member> member = memberRepository.findByNickname(nickname);
//        long points = 0;
//        member.get().update(points + point);

        return levelandpoint;
    }


}
