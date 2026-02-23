package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.Notice;
import com.ctn.commonauthentication.util.enums.NoticeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findByCategoryOrderByCreatedDesc(NoticeCategory category, Pageable pageable);
    Page<Notice> findAllByOrderByCreatedDesc(Pageable pageable);
}
