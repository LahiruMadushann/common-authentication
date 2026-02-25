package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.dto.PublicNoticeDto;
import com.ctn.commonauthentication.entity.Notice;
import com.ctn.commonauthentication.util.enums.NoticeApprovalStatus;
import com.ctn.commonauthentication.util.enums.NoticeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

public interface INoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findByCategoryOrderByCreatedDesc(NoticeCategory category, Pageable pageable);
    Page<Notice> findAllByOrderByCreatedDesc(Pageable pageable);
    @Query("SELECT new com.ctn.commonauthentication.dto.PublicNoticeDto(n.id, n.title, n.content, n.author, n.created, n.published, n.updatedLast, n.approvedBy, n.updatedBy, n.isPin, n.category) " +
            "FROM Notice n " +
            "WHERE n.approvalStatus = :noticeApprovalStatus " +
            "AND n.isPublic = true " +
            "AND n.published < :currentTime " +
            "AND (n.expireAfter > :currentTime OR n.expireAfter IS NULL) " +
            "AND (:accessibleNoticeCategories IS NULL OR n.category = :accessibleNoticeCategories OR n.category = 'GENERAL') "+
            "ORDER BY n.created DESC")
    Page<PublicNoticeDto> findPublicNotices(
            @Param("noticeApprovalStatus") NoticeApprovalStatus noticeApprovalStatus,
            @Param("currentTime") Timestamp currentTime,
            @Nullable @Param("accessibleNoticeCategories") NoticeCategory accessibleNoticeCategories,
            Pageable pageable);
}
