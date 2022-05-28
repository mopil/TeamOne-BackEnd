package com.mjuteam2.TeamOne.message.service;

import com.mjuteam2.TeamOne.caution.domain.Caution;
import com.mjuteam2.TeamOne.caution.dto.CautionListResponse;
import com.mjuteam2.TeamOne.caution.dto.CautionResponse;
import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.message.domain.MessageRoom;
import com.mjuteam2.TeamOne.message.dto.MessageRoomListResponse;
import com.mjuteam2.TeamOne.message.dto.MessageRoomRequestForm;
import com.mjuteam2.TeamOne.message.dto.MessageRoomResponse;
import com.mjuteam2.TeamOne.message.exception.MessageRoomException;
import com.mjuteam2.TeamOne.message.repository.MessageRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public MessageRoomResponse createMessageRoom(MessageRoomRequestForm form) {

        Long senderUserId = form.getSenderId();
        System.out.println("senderUserId = " + senderUserId);
        Member sender = memberRepository.findById(senderUserId).orElseThrow(() -> new MemberException("멤버가 존재하지 않습니다."));

        Long receiverUserId = form.getReceiverId();
        System.out.println("receiverUserId = " + receiverUserId);
        Member receiver = memberRepository.findById(receiverUserId).orElseThrow(() -> new MemberException("멤버가 존재하지 않습니다."));

        Optional<MessageRoom> messageRoom1 = messageRoomRepository.existsMessageRoomByReceiverAndSender(senderUserId, receiverUserId);
        Optional<MessageRoom> messageRoom2 = messageRoomRepository.existsMessageRoomByReceiverAndSender(receiverUserId, senderUserId);

        // 중복 체크
        if (messageRoom1.isEmpty() && messageRoom2.isEmpty()) {
            MessageRoom messageRoom = MessageRoom.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .build();

            MessageRoom saveMessageRoom = messageRoomRepository.save(messageRoom);
            return saveMessageRoom.toResponse();
        } else {
            throw new MessageRoomException("이미 채팅방이 있습니다.");
        }


    }

    /**
     * 채팅방 조회
     */
    public MessageRoomResponse findMessageRoom(Long messageRoomId) {
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).orElseThrow(() -> new MessageRoomException("채팅방을 찾을 수 없습니다."));
        return messageRoom.toResponse();
    }

    /**
     * 채팅방 조회 (유저 기준)
     */
    public MessageRoomListResponse findMessageRoomByReceiver(Long receiverId) {
        List<MessageRoom> messageRoomList = messageRoomRepository.findAllByReceiverId(receiverId);
        List<MessageRoomResponse> result = new ArrayList<>();
        messageRoomList.forEach(messageRoom -> result.add(messageRoom.toResponse()));
        return new MessageRoomListResponse(result);
    }


    /**
     * 채팅방 삭제
     */
    public void delete(Long messageRoomId) {
        messageRoomRepository.deleteById(messageRoomId);
    }

}
