package com.hh99.nearby.util;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class Graph {

    private final MemberRepository memberRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void SevenDaysGraph() {
        List<Member> memberList = memberRepository.findAll();
        if (memberList.size() != 0) {
            LocalDate now  = LocalDate.now();
            for (int j = 0; j < memberList.size(); j++) {

                List<Long> sevendaysGraph = new ArrayList<>();
                long oneTime = 0;
                long twoTime = 0;
                long threeTime = 0;
                long fourTime = 0;
                long fiveTime = 0;
                long sixTime = 0;
                long sevenTime = 0;

//                List<MemberChallenge> memberChallengeSevenDay = memberChallengeRepository.sevenday();
                List<MemberChallenge> memberChallengeSevenDay = memberChallengeRepository.findAllByStartDayEquals(now.minusDays(6));
                for (int i = 0; i < memberChallengeSevenDay.size(); i++) {
                    if (memberList.get(j).getId().equals(memberChallengeSevenDay.get(i).getMember().getId())) {
                        sevenTime += memberChallengeSevenDay.get(i).getRealTime();
                    }
                }

//                List<MemberChallenge> memberChallengeSixDay = memberChallengeRepository.sixday();
                List<MemberChallenge> memberChallengeSixDay = memberChallengeRepository.findAllByStartDayEquals(now.minusDays(5));
                for (int i = 0; i < memberChallengeSixDay.size(); i++) {
                    if (memberList.get(j).getId().equals(memberChallengeSixDay.get(i).getMember().getId())) {
                        sixTime += memberChallengeSixDay.get(i).getRealTime();
                    }
                }

//                List<MemberChallenge> memberChallengeFiveDay = memberChallengeRepository.fiveday();
                List<MemberChallenge> memberChallengeFiveDay = memberChallengeRepository.findAllByStartDayEquals(now.minusDays(4));
                for (int i = 0; i < memberChallengeFiveDay.size(); i++) {
                    if (memberList.get(j).getId().equals(memberChallengeFiveDay.get(i).getMember().getId())) {
                        fiveTime += memberChallengeFiveDay.get(i).getRealTime();
                    }
                }

//                List<MemberChallenge> memberChallengeFourDay = memberChallengeRepository.fourday();
                List<MemberChallenge> memberChallengeFourDay = memberChallengeRepository.findAllByStartDayEquals(now.minusDays(3));
                for (int i = 0; i < memberChallengeFourDay.size(); i++) {
                    if (memberList.get(j).getId().equals(memberChallengeFourDay.get(i).getMember().getId())) {
                        fourTime += memberChallengeFourDay.get(i).getRealTime();
                    }
                }

//                List<MemberChallenge> memberChallengeThreeDay = memberChallengeRepository.threeday();
                List<MemberChallenge> memberChallengeThreeDay = memberChallengeRepository.findAllByStartDayEquals(now.minusDays(2));
                for (int i = 0; i < memberChallengeThreeDay.size(); i++) {
                    if (memberList.get(j).getId().equals(memberChallengeThreeDay.get(i).getMember().getId())) {
                        threeTime += memberChallengeThreeDay.get(i).getRealTime();
                    }
                }

//                List<MemberChallenge> memberChallengeTwoDay = memberChallengeRepository.twoday();
                List<MemberChallenge> memberChallengeTwoDay = memberChallengeRepository.findAllByStartDayEquals(now.minusDays(1));
                for (int i = 0; i < memberChallengeTwoDay.size(); i++) {
                    if (memberList.get(j).getId().equals(memberChallengeTwoDay.get(i).getMember().getId())) {
                        twoTime += memberChallengeTwoDay.get(i).getRealTime();
                    }
                }
                //오늘
//                List<MemberChallenge> memberChallengeOneDay = memberChallengeRepository.oneday();
                List<MemberChallenge> memberChallengeOneDay = memberChallengeRepository.findAllByStartDayEquals(now);
                for (int i = 0; i < memberChallengeOneDay.size(); i++) {
                    if (memberList.get(j).getId().equals(memberChallengeOneDay.get(i).getMember().getId())) {
                        oneTime += memberChallengeOneDay.get(i).getRealTime();
                    }
                }



                sevendaysGraph.add(sevenTime);
                sevendaysGraph.add(sixTime);
                sevendaysGraph.add(fiveTime);
                sevendaysGraph.add(fourTime);
                sevendaysGraph.add(threeTime);
                sevendaysGraph.add(twoTime);
                sevendaysGraph.add(oneTime);

                memberList.get(j).update(sevendaysGraph);
            }

        }
    }
}
