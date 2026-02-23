package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.dto.OperatorAppraisal;
import com.ctn.commonauthentication.dto.UpdatePhotosDTO;
import com.ctn.commonauthentication.entity.AppraisalRequestInformation;
import com.ctn.commonauthentication.repository.IAssessedAppraisalsRepository;
import com.ctn.commonauthentication.repository.IShopConditionEmailsRepository;
import com.ctn.commonauthentication.repository.ShopRepository;
import com.ctn.commonauthentication.service.AppraisalRequestInformationService;
import com.ctn.commonauthentication.service.IInternalEmailService;
import com.ctn.commonauthentication.util.AppraisalRequestMapper;
import jakarta.mail.internet.AddressException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/appraisals")
public class AppraisalRequestInformationController {

    private final AppraisalRequestInformationService service;
    private final AppraisalRequestMapper appraisalRequestMapper;
    private final IAssessedAppraisalsRepository assessedRepo;
    private final IShopConditionEmailsRepository shopEmailRepository;
    private final IInternalEmailService internalEmailService;
    private final ShopRepository shopRepository;

    @PatchMapping("/{id}/photos")
    public ResponseEntity<AppraisalRequestInformation> updatePhotos(@PathVariable Long id, @RequestBody UpdatePhotosDTO dto) {
        Optional<AppraisalRequestInformation> updatedAppraisal = service.updatePhotos(id, dto);

        OperatorAppraisal operatorAppraisal = appraisalRequestMapper.loadAppraisalById(id);

        assessedRepo.findAllByAppraisalid(id.toString()).forEach(assessed -> {;
            shopEmailRepository.findByIdShopId(assessed.getShopid()).forEach(shopEmail -> {
                try {
                    internalEmailService.sendPhotoInformationUploadEmail(shopEmail.getId().getEmail(), operatorAppraisal, shopRepository.findById(assessed.getShopid()).get().getName());
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (AddressException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        return updatedAppraisal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
