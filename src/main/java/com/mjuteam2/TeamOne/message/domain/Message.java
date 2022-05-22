package com.mjuteam2.TeamOne.message.domain;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.message.dto.MessageResponse;
import com.mjuteam2.TeamOne.util.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
public class Message extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_room_id")
    private MessageRoom messageRoom;

    public MessageResponse toResponse() {
        return MessageResponse.builder()
                .messageId(id)
                .senderUserId(sender.getUserId())
                .senderId(sender.getId())
                .receiverUserId(receiver.getUserId())
                .receiverId(receiver.getId())
                .content(content)
                .messageRoomId(messageRoom.getId())
                .createdDate(getCreatedDate())
                .build();
    }

    @Builder
    public Message(Member sender, Member receiver, String content, MessageRoom messageRoom) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.messageRoom = messageRoom;
    }

    protected Message() {}
}
