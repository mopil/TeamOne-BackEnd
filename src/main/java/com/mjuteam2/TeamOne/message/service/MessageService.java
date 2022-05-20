package com.mjuteam2.TeamOne.message.service;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.message.domain.Message;
import com.mjuteam2.TeamOne.message.domain.MessageRoom;
import com.mjuteam2.TeamOne.message.dto.MessageRequestForm;
import com.mjuteam2.TeamOne.message.dto.MessageResponse;
import com.mjuteam2.TeamOne.message.exception.MessageException;
import com.mjuteam2.TeamOne.message.exception.MessageRoomException;
import com.mjuteam2.TeamOne.message.repository.MessageRepository;
import com.mjuteam2.TeamOne.message.repository.MessageRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MessageRoomRepository messageRoomRepository;

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->  new MemberException("해당 회원이 없습니다."));
    }

    // 디비에서 가져온 메시지 리스트를 응답 리스트로 변환
    private List<MessageResponse> convert(List<Message> messages) {
        List<MessageResponse> result = new ArrayList<>();
        messages.forEach(message -> result.add(message.toResponse()));
        return result;
    }

    /**
     * 메시지 생성
     */
    public MessageResponse createMessage(MessageRequestForm form) {
        // 보내는 사람 디비에서 조회
        Member sender = getMember(form.getSenderId());

        // 받는 사람 디비에서 조회
        Member receiver = getMember(form.getReceiverId());

        // 채팅 방 조회
        Long messageRoomId = form.getMessageRoomId();
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).orElseThrow(() -> new MessageRoomException("채팅방이 존재하지 않습니다."));

        // 메시지 생성
        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(form.getContent())
                .messageRoom(messageRoom)
                .build();

        // 메시지 디비에 저장
        messageRepository.save(message);

        // dto 로 변환해서 리턴
        return message.toResponse();
    }

    /**
     * 메시지 조회
     */
    // 메시지 하나 조회
    public MessageResponse findById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException("쪽지 조회 오류."))
                .toResponse();
    }

    // 보내는 사람 기준으로 보낸 메시지 전체 조회
    public List<MessageResponse> findAllSent(Long senderId) {
        List<Message> messages = messageRepository.findAllBySenderId(senderId);
        return convert(messages);
    }

    // 받은 사람 기준으로 받은 메시지 전체 조회
    public List<MessageResponse> findAllReceived(Long receiverId) {
        List<Message> messages = messageRepository.findAllByReceiverId(receiverId);
        return convert(messages);
    }

    // 채팅방 기준 메시지 조회
    public List<MessageResponse> findByMessageRoomId(Long messageRoomId) {
        List<Message> messages = messageRepository.findByMessageRoomId(messageRoomId);
        return convert(messages);
    }

    /**
     * 메시지 삭제
     */
    // 메시지 하나 삭제
    public void delete(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    // 받은 메시지 전체 삭제
    public void deleteAll(Long receiverId) {
        List<Message> receivedMessages = messageRepository.findAllByReceiverId(receiverId);
        messageRepository.deleteAll(receivedMessages);
    }

}
