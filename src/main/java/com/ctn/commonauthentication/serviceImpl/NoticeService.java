package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.PublicNoticeDto;
import com.ctn.commonauthentication.entity.Notice;
import com.ctn.commonauthentication.repository.INoticeRepository;
import com.ctn.commonauthentication.service.INoticeService;
import com.ctn.commonauthentication.util.enums.NoticeApprovalStatus;
import com.ctn.commonauthentication.util.enums.NoticeCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService implements INoticeService {

    private final INoticeRepository noticeRepository;

    @Override
    public Page<Notice> getNoticesByCategoryWithSort(NoticeCategory category, Pageable pageable) {
        return noticeRepository.findByCategoryOrderByCreatedDesc(category, pageable);
    }

    @Override
    public Page<Notice> getAllNoticesWithSort(Pageable pageable) {
        return noticeRepository.findAllByOrderByCreatedDesc(pageable);
    }

    @Override
    public Optional<PublicNoticeDto> getPublicNoticeById(Long id) {

        Optional<Notice> notice = noticeRepository.findById(id);

        if (notice.isPresent() &&
                notice.get().isPublic() &&
                notice.get().getApprovalStatus() == NoticeApprovalStatus.APPROVED &&
                notice.get().getPublished().before(Timestamp.valueOf(LocalDateTime.now())) &&
                (notice.get().getExpireAfter() == null || notice.get().getExpireAfter().after(Timestamp.valueOf(LocalDateTime.now())))){

            return Optional.of(new PublicNoticeDto(
                    notice.get().getId(),
                    notice.get().getTitle(),
                    notice.get().getContent(),
                    notice.get().getAuthor(),
                    notice.get().getCreated(),
                    notice.get().getPublished(),
                    notice.get().getUpdatedLast(),
                    notice.get().getApprovedBy(),
                    notice.get().getUpdatedBy(),
                    notice.get().isPin(),
                    notice.get().getCategory()
            ));
        }

        return Optional.empty();

    }

    @Override
    public Page<PublicNoticeDto> getPublicNotices(List<NoticeCategory> accessibleNoticeCategories, Pageable pageable) {
        NoticeCategory noticeCategory = null;
        if (accessibleNoticeCategories.contains(NoticeCategory.BUYER) && !accessibleNoticeCategories.contains(NoticeCategory.SELLER)) {
            noticeCategory = NoticeCategory.BUYER;
        } else if (accessibleNoticeCategories.contains(NoticeCategory.SELLER) && !accessibleNoticeCategories.contains(NoticeCategory.BUYER)) {
            noticeCategory = NoticeCategory.SELLER;
        }

        return noticeRepository.findPublicNotices(NoticeApprovalStatus.APPROVED, Timestamp.valueOf(LocalDateTime.now()), noticeCategory ,  pageable);
    }
}
