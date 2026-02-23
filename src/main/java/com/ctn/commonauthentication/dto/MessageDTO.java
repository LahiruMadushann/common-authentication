package com.ctn.commonauthentication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime createdAt;
    private boolean isResponded;
    private boolean isNotified;
    private boolean isFirstTime;
    private String fileUrl;
    private String fileName;
    private String fileType;
    private String receiverName;

    public MessageDTO(Long id, Long senderId, Long receiverId, String content, LocalDateTime createdAt,
                      boolean isResponded, boolean isNotified, boolean isFirstTime, String fileUrl,
                      String fileName, String fileType, String receiverName) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createdAt = createdAt;
        this.isResponded = isResponded;
        this.isNotified = isNotified;
        this.isFirstTime = isFirstTime;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.fileType = fileType;
        this.receiverName = receiverName;
    }
}
