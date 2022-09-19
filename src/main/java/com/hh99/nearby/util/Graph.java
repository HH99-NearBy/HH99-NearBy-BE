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
public class Graph {

    private final MemberRepository memberRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    public List<Long> SevenDaysGraph(String nickname){
        List<Long> sevendaysGraph = new ArrayList<>();
        long oneTime = 0;
        long twoTime = 0;
        long threeTime = 0;
        long fourTime = 0;
        long fiveTime = 0;
        long sixTime = 0;
        long sevenTime = 0;
        Optional<Member> member = memberRepository.findByNickname(nickname);
        List<MemberChallenge> memberChallengeToday = memberChallengeRepository.today();

        System.out.println(memberChallengeToday.get(0).getMember().getNickname());
        System.out.println(memberChallengeToday.get(0).getRealTime());

        for (int i = 0; i<memberChallengeToday.size(); i++){
            if (member.get().getNickname().equals(memberChallengeToday.get(i).getMember().getNickname())){
                oneTime += memberChallengeToday.get(i).getRealTime();
            }
        }
        sevendaysGraph.add(sevenTime);
        sevendaysGraph.add(sixTime);
        sevendaysGraph.add(fiveTime);
        sevendaysGraph.add(fourTime);
        sevendaysGraph.add(threeTime);
        sevendaysGraph.add(twoTime);
        sevendaysGraph.add(oneTime);

        System.out.println(sevendaysGraph.get(6));

        return sevendaysGraph;
    }
}
