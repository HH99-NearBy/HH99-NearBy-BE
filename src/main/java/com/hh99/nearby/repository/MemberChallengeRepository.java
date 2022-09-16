package com.hh99.nearby.repository;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge,Long> {
    Optional<MemberChallenge> findByMember_IdAndChallenge_Id(Long memberId,Long ChallengeId);

    List<MemberChallenge> findByMember(Member member);

    Optional<MemberChallenge> findByMember_Id(Long memberId);

    List<MemberChallenge> findAllByMember_nickname(String nickname);

//    MemberChallenge findByMember_nickname(String nickname);


}
