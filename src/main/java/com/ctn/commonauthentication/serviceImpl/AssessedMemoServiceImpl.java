package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.AssessedMemoPreviewDto;
import com.ctn.commonauthentication.entity.AssessedMemo;
import com.ctn.commonauthentication.repository.AssessedMemoRepository;
import com.ctn.commonauthentication.service.AssessedMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessedMemoServiceImpl implements AssessedMemoService {

    private final AssessedMemoRepository assessedMemoRepository;

    @Override
    public List<AssessedMemoPreviewDto> findAllById_AppraisalidAndId_Shopid(long appraisalID, int shopID) {
        return assessedMemoRepository.findAllById_AppraisalIdAndId_ShopId(appraisalID, shopID).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AssessedMemoPreviewDto convertToDto(AssessedMemo assessedMemo) {
        return new AssessedMemoPreviewDto(
                assessedMemo.getId().getId(),
                assessedMemo.getMemoText()
        );
    }
}
