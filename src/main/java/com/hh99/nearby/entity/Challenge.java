package com.hh99.nearby.entity;


import com.hh99.nearby.challenge.dto.ChallengeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Challenge extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; //챌리지 제목

    @Column()
    private String challengeImg; //챌리지 대표 이미지

    @Column(nullable = false)
    private LocalDate startDay; //챌리지 시작 일자

    @Column(nullable = false)
    private LocalTime startTime; //챌리지 시작 시간

    @Column(nullable = false)
    private Long targetTime; //챌린지 진행시간

    @Column(nullable = false)
    private LocalDateTime endTime; //챌린지 종료시간

    @Column(nullable = false)
    private Long limitPeople; //챌린지 제한인원

    @Column(nullable = false)
    private String content; //챌린지 내용

    @Column()
    private String notice; //챌린지 공지사항

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer; //챌린지 작성자

    @Column(name="challengeTags")
    @ElementCollection(targetClass = String.class)
    private List<String> challengeTag; //챌린지에 사용된 태그리스트

    public void update(ChallengeRequestDto challengeRequestDto) {
        LocalDate localDate = LocalDate.parse(challengeRequestDto.getStartDay());   //챌리지 시작일자
        LocalTime localTime = LocalTime.parse(challengeRequestDto.getStartTime());   //챌리지 시작시간

        //하루나 년도가 넘어가는 상황이 발생할수 있으므로 LocalDateTime객체로 만든후에 진행시간을 더해주는 방식으로 구현
        LocalDateTime localDateTime = localDate.atTime(localTime);
        localDateTime = localDateTime.plusMinutes(challengeRequestDto.getTargetTime());

        LocalDateTime endTime = localDateTime;  //챌리지 종료시간

        String defaultImg = "https://user-images.githubusercontent.com/74406343/188258363-9a049b49-eba3-4518-9674-391d7887c5f8.png";

        title = challengeRequestDto.getTitle();
        challengeImg = challengeRequestDto.getChallengeImg();
        startDay = localDate;
        startTime = localTime;
        targetTime = challengeRequestDto.getTargetTime();
        this.endTime = endTime;
        limitPeople = challengeRequestDto.getLimit();
        content = challengeRequestDto.getContent();
        notice = challengeRequestDto.getNotice();
        challengeTag = challengeRequestDto.getChallengeTag();
    }

    @OneToMany(mappedBy="challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberChallenge> memberChallengeList;

    //    public void update(PostRequestDto postRequestDto,Member member){
//    public void update(PostRequestDto postRequestDto){
//        this.content = postRequestDto.getContent();
////        this.member = member;
//    }

//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Likes> postLikeList;
//
//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Hashtags> hashtagsList;
//
//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Image> imageList;
//
//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> commentList;


}