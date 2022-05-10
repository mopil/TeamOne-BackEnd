package com.mjuteam2.TeamOne.message.service;

import com.mjuteam2.TeamOne.member.domain.Member;
import com.mjuteam2.TeamOne.member.exception.MemberException;
import com.mjuteam2.TeamOne.member.repository.MemberRepository;
import com.mjuteam2.TeamOne.message.domain.Message;
import com.mjuteam2.TeamOne.message.dto.MessageRequestForm;
import com.mjuteam2.TeamOne.message.dto.MessageResponse;
import com.mjuteam2.TeamOne.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->  new MemberException("해당 회원이 없습니다."));
    }

    /**
     * 메시지 생성
     */
    public MessageResponse createMessage(MessageRequestForm form) {
        // 보내는 사람 디비에서 조회
        Member sender = getMember(form.getSenderId());

        // 받는 사람 디비에서 조회
        Member receiver = getMember(form.getReceiverId());

        // 메시지 생성
        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(form.getContent())
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

    // 보내는 사람 기준으로 보낸 메시지 전체 조회

    // 받은 사람 기준으로 받은 메시지 전체 조회

    /**
     * 메시지 삭제
     */
}
