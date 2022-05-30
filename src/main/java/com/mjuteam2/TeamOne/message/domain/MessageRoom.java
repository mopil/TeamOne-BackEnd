package com.mjuteam2.TeamOne.message.domain;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.message.dto.MessageRoomResponse;
import com.mjuteam2.TeamOne.util.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoom extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "message_room_id")
    private Long id;

    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Builder
    public MessageRoom(Member sender, Member receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public MessageRoomResponse toResponse() {
        return MessageRoomResponse.builder()
                .messageRoomId(id)
                .createdDate(getCreatedDate())
                .senderId(sender.getId())
                .senderUserId(sender.getUserId())
                .receiverId(receiver.getId())
                .receiverUserId(receiver.getUserId())
                .receiverNickname(receiver.getNickname())
                .build();
    }

}
