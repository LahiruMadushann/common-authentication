package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.entity.AssessedId;
import com.ctn.commonauthentication.repository.IAssessedRepository;
import com.ctn.commonauthentication.service.AIFinalOfferService;
import com.ctn.commonauthentication.service.IAssessedAppraisalService;
import com.ctn.commonauthentication.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssessedAppraisalService implements IAssessedAppraisalService {

    private final IAssessedRepository assessedRepository;
    private final SecurityUtils securityUtils;
    private final AIFinalOfferService aiFinalOfferService;

    @Override
    public Optional<Assessed> findById(AssessedId id) {
        return assessedRepository.findById(id);
    }

    @Override
    public void updateAssessed(Assessed assessed) {
        assessedRepository.save(assessed);
        try {
            String role = securityUtils.getRole();
            if (role.equals("ROLE_ADMIN")) {
                if (assessed.getSendToAISystem() == null || !assessed.getSendToAISystem()) {
                    return;
                }
            }
            if(role.equals("ROLE_USER")){
                if(assessedRepository.hasAdminAmount(assessed.getId())){
                    return;
                }
            }
            if (role.equals("ROLE_BUYER")){
                if(assessedRepository.hasAdminAmount(assessed.getId())){
                    return;
                }

                if(assessedRepository.hasFinalOffer(assessed.getId())){
                    return;
                }

            }
            aiFinalOfferService.aiFinalOfferUpdate(assessed.getId().getAppraisalid(), assessed.getId().getShopid());

        } catch(Exception e){
            log.warn("Failed to update AI final offer for assessed: {}", assessed.getId(), e);
        }

    }
}
