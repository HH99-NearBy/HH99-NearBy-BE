package com.hh99.nearby.util;


import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LevelCheck {

    private final MemberChallengeRepository memberChallengeRepository;

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

        return levelandpoint;
    }
}
