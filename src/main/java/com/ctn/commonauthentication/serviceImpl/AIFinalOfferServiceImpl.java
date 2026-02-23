package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.client.AIFinalOfferClient;
import com.ctn.commonauthentication.dto.AIFinalOfferRequestDto;
import com.ctn.commonauthentication.dto.AIFinalOfferResponseDto;
import com.ctn.commonauthentication.service.AIFinalOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIFinalOfferServiceImpl implements AIFinalOfferService {

    private final AIFinalOfferResponseBuilder aiFinalOfferResponseBuilder;
    private final AIFinalOfferClient aiFinalOfferClient;

    @Override
    public AIFinalOfferResponseDto aiFinalOfferUpdate(Long appraisalId, Integer shopId) {
        AIFinalOfferRequestDto aiFinalOfferRequestDto = aiFinalOfferResponseBuilder.build(appraisalId, shopId);
        return aiFinalOfferClient.callAIFinalOfferAPI(aiFinalOfferRequestDto);
    }
}
