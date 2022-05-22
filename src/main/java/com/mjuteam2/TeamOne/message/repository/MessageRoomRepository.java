package com.mjuteam2.TeamOne.message.repository;

import com.mjuteam2.TeamOne.message.domain.Message;
import com.mjuteam2.TeamOne.message.domain.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {

    @Query("SELECT m FROM MessageRoom m WHERE m.receiver.id = :receiverId OR m.sender.id = :receiverId")
    List<MessageRoom> findAllByReceiverId(@Param("receiverId") Long receiverId);
}
