package com.mjuteam2.TeamOne.message.service;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.message.domain.MessageRoom;
import com.mjuteam2.TeamOne.message.dto.MessageRoomForm;
import com.mjuteam2.TeamOne.message.exception.MessageRoomException;
import com.mjuteam2.TeamOne.message.repository.MessageRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅방 생성
     */
    public MessageRoom createMessageRoom(MessageRoomForm form) {
        Long senderUserId = form.getSenderUserId();
        Member sender = memberRepository.findById(senderUserId).orElseThrow(() -> new MemberException("멤버가 존재하지 않습니다."));

        Long receiverUserId = form.getSenderUserId();
        Member receiver = memberRepository.findById(receiverUserId).orElseThrow(() -> new MemberException("멤버가 존재하지 않습니다."));

        MessageRoom messageRoom = MessageRoom.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        MessageRoom saveMessageRoom = messageRoomRepository.save(messageRoom);
        return saveMessageRoom;
    }

    /**
     * 채팅방 조회
     */
    public MessageRoom findMessageRoom(Long messageRoomId) {
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).orElseThrow(() -> new MessageRoomException("채팅방을 찾을 수 없습니다."));
        return messageRoom;
    }

    /**
     * 채팅방 조회 (유저 기준)
     */
    public List<MessageRoom> findMessageRoomByReceiver(Long receiverId) {
        return messageRoomRepository.findAllByReceiverId(receiverId);
    }


    /**
     * 채팅방 삭제
     */
    public void delete(Long messageRoomId) {
        messageRoomRepository.deleteById(messageRoomId);
    }

}
