package com.hh99.nearby.util;


import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LevelCheck {

    private final MemberChallengeRepository memberChallengeRepository;

    public Long levelCheck(String nickname) {
        MemberChallenge memberChallenge = memberChallengeRepository.findByMember_nickname(nickname); // 리얼타임을 가져오기위한 db접근
        Long level; // 레벨 선언
        if (memberChallenge == null) { // 안에 값이 없다면
            level = 0L; // 무조건 0
        } else {
            level = memberChallenge.getRealTime() / 70; // 값이 있다면 분으로 계산하기 떄문에 70분에 레벨 1씩
        }
        return level; //레벨값 넘기기
    }


}
