package com.mjuteam2.TeamOne.message.repository;

import com.mjuteam2.TeamOne.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
