package com.hh99.nearby.util;


import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LevelCheck {

    private final MemberChallengeRepository memberChallengeRepository;

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
        System.out.println(levelandpoint.add(point));
        System.out.println(levelandpoint.add(level));
        System.out.println(levelandpoint.get(0));
        System.out.println(levelandpoint.get(1));

        return levelandpoint;
    }


}
