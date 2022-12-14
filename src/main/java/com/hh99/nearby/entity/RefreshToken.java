package com.hh99.nearby.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RefreshToken extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Member member;

    @Column(nullable = false)
    private String token;

    public void update(String refreshToken) {
        this.token = refreshToken;
    }
}
