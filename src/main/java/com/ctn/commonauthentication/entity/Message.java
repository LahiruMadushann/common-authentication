package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime timeStamp;

    @Column(columnDefinition = "boolean default false")
    private boolean isResponded;

    @Column(columnDefinition = "boolean default false")
    private boolean isNotified;

    @Column(name = "is_first_time", columnDefinition = "boolean default false")
    private boolean isFirstTime;

    @Column(name = "fileurl")
    private String fileUrl;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "filetype")
    private String fileType;
}
