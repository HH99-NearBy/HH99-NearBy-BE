package com.hh99.nearby.wishlist.service;

import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.WishList;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public ResponseEntity<?> createWishList(@PathVariable Long id, UserDetails user){
        Optional<Challenge> challenge = challengeRepository.findById(id);
        if (challenge.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("msg","잘못된 챌린지 번호입니다."));
        }
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());
        if (member.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("msg","없는 회원입니다."));
        }
        Optional<WishList> optionalWishList =
                wishListRepository.findByChallengeAndMember(challenge.get(),member.get());
        if (optionalWishList.isPresent()){
            return ResponseEntity.badRequest().body(Map.of("msg","이미 찜 헀습니다."));
        }

        WishList wishList = WishList.builder()
                .challenge(challenge.get())
                .member(member.get())
                .build();
        wishListRepository.save(wishList);
        return ResponseEntity.ok().body(Map.of("msg","찜 등록을 완료했습니다."));
    }

    @Transactional
    public ResponseEntity<?> deleteWishList(@PathVariable Long id,UserDetails user){

        Optional<Challenge> challenge = challengeRepository.findById(id);
        if (challenge.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("msg","잘못된 챌린지 번호입니다."));
        }
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());
        if (member.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("msg","없는 회원입니다."));
        }
        Optional<WishList> wishList =
                wishListRepository.findByChallengeAndMember(challenge.get(),member.get());
        if (wishList.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("msg","찜 하지 않으셨습니다."));
        }
        if (!user.getUsername().equals(wishList.get().getMember().getNickname())){
            return ResponseEntity.badRequest().body(Map.of("msg","일치하는 회원이 아닙니다."));
        }
        wishListRepository.delete(wishList.get());
        return ResponseEntity.ok().body(Map.of("msg","찜 등록을 취소하셨습니다."));
    }
}
