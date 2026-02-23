package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.dto.PublicNoticeDto;
import com.ctn.commonauthentication.entity.Notice;
import com.ctn.commonauthentication.service.INoticeService;
import com.ctn.commonauthentication.serviceImpl.NoticeAccessManager;
import com.ctn.commonauthentication.util.enums.NoticeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/notice")
public class NoticeController {

    private final INoticeService noticeService;

    @GetMapping("/private")
    public ResponseEntity<Page<Notice>> getAllNotices(@RequestParam(value = "category", required = false) NoticeCategory category,
                                                      Pageable pageable
    ) {

        if (category != null) {
            return ResponseEntity.ok(noticeService.getNoticesByCategoryWithSort(category, pageable));
        }

        return ResponseEntity.ok(noticeService.getAllNoticesWithSort(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<PublicNoticeDto> getPublicNoticeById(@PathVariable("id") Long id) {
        Optional<PublicNoticeDto> notice = noticeService.getPublicNoticeById(id);

        if (notice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!new NoticeAccessManager().hasAccessToCategory(notice.get().getCategory())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(notice.get());
    }
}
