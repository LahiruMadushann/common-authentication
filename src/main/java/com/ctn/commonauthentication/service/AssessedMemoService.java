package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.AssessedMemoPreviewDto;

import java.util.List;

public interface AssessedMemoService {
    List<AssessedMemoPreviewDto> findAllById_AppraisalidAndId_Shopid(long appraisalID, int shopID);
}
