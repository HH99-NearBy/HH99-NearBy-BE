package com.hh99.nearby.repository;


import com.hh99.nearby.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String Nickname);

    Optional<Member> findById(Long id);

//    List<Member> findAllByOrderByPointsDesc();

    @Query(value = "Select p from Member p order by p.points desc ")
    List<Member> rank();

    @Query(value = "Select p from Member p order by p.points desc ")
    Slice<Member> rank(Pageable pageable);

    @Query(value = "Select p from Member p order by p.points desc ")
    Page<Member> rank2(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m SET m.myRank =:myRank where m.nickname =:nickname")
    void updateRank(@Param(value = "myRank") Long myRank,@Param(value = "nickname") String nickname);
}
