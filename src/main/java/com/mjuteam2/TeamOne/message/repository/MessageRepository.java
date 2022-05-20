package com.mjuteam2.TeamOne.message.repository;

import com.mjuteam2.TeamOne.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // 보낸 사람 기준으로 보낸 메시지 전체 조회
    @Query("SELECT m FROM Message m WHERE m.sender.id = :senderId")
    List<Message> findAllBySenderId(@Param("senderId") Long senderId);

    // 받은 사람을 기준으로 받은 메시지 전체 조회
    @Query("SELECT m FROM Message m WHERE m.receiver.id = :receiverId")
    List<Message> findAllByReceiverId(@Param("receiverId") Long receiverId);

    // 채팅방 기준 메시지 조회
    @Query("SELECT m from Message m WHERE m.messageRoom.id = :messageRoomId")
    List<Message> findByMessageRoomId(@Param("messageRoomId") Long messageRoomId);
}
