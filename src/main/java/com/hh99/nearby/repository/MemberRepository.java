package com.hh99.nearby.repository;


import com.hh99.nearby.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String Nickname);

    Optional<Member> findById(Long id);

//    List<Member> findAllByOrderByPointsDesc();

    @Query(value = "Select p from Member p order by p.points desc ")
    List<Member> rank();
}
