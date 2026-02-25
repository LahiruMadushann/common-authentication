package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.PublicNoticeDto;
import com.ctn.commonauthentication.entity.Notice;
import com.ctn.commonauthentication.util.enums.NoticeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface INoticeService {
    Page<Notice> getNoticesByCategoryWithSort(NoticeCategory category, Pageable pageable);
    Page<Notice> getAllNoticesWithSort(Pageable pageable);
    Optional<PublicNoticeDto> getPublicNoticeById(Long id);
    Page<PublicNoticeDto> getPublicNotices(@Nullable List<NoticeCategory> accessibleNoticeCategories, Pageable pageable);

}
