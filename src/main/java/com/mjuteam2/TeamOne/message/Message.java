package com.mjuteam2.TeamOne.message;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity @Getter @Setter
public class Message {

    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String sender;
    private String receiver;
    private String content;

    @CreationTimestamp
    private LocalDate createdAt;
}
