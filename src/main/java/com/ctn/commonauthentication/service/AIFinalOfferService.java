package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.AIFinalOfferResponseDto;

public interface AIFinalOfferService {
    AIFinalOfferResponseDto aiFinalOfferUpdate(Long appraisalId, Integer shopId);
}
