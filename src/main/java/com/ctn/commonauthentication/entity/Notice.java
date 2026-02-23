package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.util.enums.NoticeApprovalStatus;
import com.ctn.commonauthentication.util.enums.NoticeCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@Entity
@Table(name = "notice")
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(nullable = false)
    private Timestamp created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(nullable = false)
    private Timestamp published;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(nullable = false)
    private Timestamp updatedLast;

    /**
     * Enum value to check if the notice is approved by ROLE_ADMIN.
     * normally ROLE_USER and ROLE_ADMIN can create a notice.
     * Enum values :
     * PENDING, APPROVED, REJECTED
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeApprovalStatus approvalStatus = NoticeApprovalStatus.PENDING;

    @Column()
    private String approvedBy;

    @Column()
    private String updatedBy;

    /**
     * Boolean value to store admins decision to make the notice public.
     * Don't Depend on this to determine the visibility of the notice.
     * This is only a one of the factor to determine the visibility of the notice.
     */
    @Column(nullable = false)
    private boolean isPublic;

    /**
     * Boolean value to check if the notice is pinned.
     * Pinned notices are shown on the top of the notice list.
     */
    @Column(nullable = false)
    private boolean isPin;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeCategory category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column()
    private Timestamp expireAfter;


}
