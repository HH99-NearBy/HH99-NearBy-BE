package com.hh99.nearby.repository;

import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList,Long> {

    Optional<WishList> findByChallengeAndMember(Challenge challenge, Member member);
}
