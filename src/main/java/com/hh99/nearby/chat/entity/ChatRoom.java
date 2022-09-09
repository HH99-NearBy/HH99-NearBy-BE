package com.hh99.nearby.chat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hh99.nearby.entity.Timestamped;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends Timestamped {
    @Id
    @Column(name = "chat_room_id")
    private Long id;

    @Column
    private String chatRoomName;

    @Column
    private Long challengeId;

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ChatRoomMember> member;

}

