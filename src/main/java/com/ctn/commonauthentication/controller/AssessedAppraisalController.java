package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.dto.AdminPrices;
import com.ctn.commonauthentication.dto.AssessedPreviewDto;
import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.entity.AssessedId;
import com.ctn.commonauthentication.repository.AppraisalRequestRepo;
import com.ctn.commonauthentication.repository.IShopConditionEmailsRepository;
import com.ctn.commonauthentication.repository.ShopInvoiceRepo;
import com.ctn.commonauthentication.service.AssessedMemoService;
import com.ctn.commonauthentication.service.IAssessedAppraisalService;
import com.ctn.commonauthentication.service.IInternalEmailService;
import jakarta.mail.internet.AddressException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("AssessedAppraisalController")
@RequiredArgsConstructor
@RequestMapping("/api/v2/assessed/")
public class AssessedAppraisalController {

    private final IAssessedAppraisalService assessedAppraisalService;
    private final AssessedMemoService assessedMemoService;
    private final ShopInvoiceRepo shopInvoiceRepo;
    private final IInternalEmailService internalEmailService;
    private final AppraisalRequestRepo appraisalRequestRepo;
    private final IShopConditionEmailsRepository shopEmailRepository;

    @GetMapping
    public ResponseEntity<Optional<AssessedPreviewDto>> getAssessedById(@RequestParam Long appraisalId,
            @RequestParam(required = false) Integer shopId, @RequestParam(required = false) Integer userId) {

        if (shopId == null && userId == null) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
        }

        if (shopId == null) {
            try {
                shopId = userIdtoShopId(userId);
            } catch (Exception e) {
                return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
            }
        }

        if (shopId == null) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
        }

        final Integer finalShopId = shopId;

        AssessedId id = new AssessedId(appraisalId, shopId);
        Optional<Assessed> assessed = assessedAppraisalService.findById(id);
        Optional<AssessedPreviewDto> assessedPreviewDto = assessed.map(this::convertToDto);
        // add memos
        assessedPreviewDto.ifPresent(assessedPreviewDto1 -> assessedPreviewDto1
                .setMemos(assessedMemoService.findAllById_AppraisalidAndId_Shopid(appraisalId, finalShopId)));

        return new ResponseEntity<>(assessedPreviewDto, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateAssessedById(@RequestParam Long appraisalId,
            @RequestParam(required = false) Integer shopId, @RequestParam(required = false) Integer userId,
            @RequestBody AssessedPreviewDto assessedPreviewDto) {
        if (assessedPreviewDto.getAdminPrices() != null && !assessedPreviewDto.getAdminPrices().isEmpty()) {
            List<AssessedPreviewDto> updatedResults = new ArrayList<>();

            for (AdminPrices adminPrice : assessedPreviewDto.getAdminPrices()) {
                Integer currentShopId = adminPrice.getShopid();

                if (currentShopId == null) {
                    continue;
                }

                AssessedId id = new AssessedId(appraisalId, currentShopId);
                Optional<Assessed> existing = assessedAppraisalService.findById(id);

                if (existing.isPresent()) {
                    Assessed assessed = existing.get();

                    // Update admin fields
                    if (adminPrice.getAdminPurchasePrice() != null) {
                        assessed.setAdminPurchasePrice(adminPrice.getAdminPurchasePrice());
                    }

                    if (adminPrice.getAdminPurchaseDateTime() != null) {
                        assessed.setAdminPurchaseDateTime(adminPrice.getAdminPurchaseDateTime());
                    }

                    if (adminPrice.getAdminEditReason() != null) {
                        assessed.setAdminEditReason(adminPrice.getAdminEditReason());
                    }

                    if (adminPrice.getSendToAISystem() != null) {
                        assessed.setSendToAISystem(adminPrice.getSendToAISystem());
                    }

                    // Update timestamp
                    assessed.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

                    assessedAppraisalService.updateAssessed(assessed);

                    // Add to results
                    Optional<AssessedPreviewDto> updated = getAssessedById(appraisalId, currentShopId, null).getBody();
                    updated.ifPresent(updatedResults::add);
                }

            }
            return new ResponseEntity<>(updatedResults, HttpStatus.OK);

        }

        // Handle single shop update (original logic)
        if (shopId == null && userId == null) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
        }

        if (shopId == null) {
            try {
                shopId = userIdtoShopId(userId);
            } catch (Exception e) {
                return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
            }
        }

        if (shopId == null) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
        }

        AssessedId id = new AssessedId(appraisalId, shopId);
        Optional<Assessed> existing = assessedAppraisalService.findById(id);
        if (existing.isPresent()) {

            Assessed assessed = existing.get();

            boolean isValueChanged = assessedPreviewDto.getValue() != null
                    && !assessedPreviewDto.getValue().equals(assessed.getValue());
            if (isValueChanged) {
                try {
                    internalEmailService.sendAppraisalAmountEntryEmail(
                            appraisalRequestRepo.findById(appraisalId).get().getCustomerEmail(),
                            appraisalRequestRepo.findById(appraisalId).get().getCustomerName());
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (AddressException e) {
                    throw new RuntimeException(e);
                }
            }

            boolean isStarValueChanged = assessedPreviewDto.getStarValue() != null
                    && !assessedPreviewDto.getStarValue().equals(assessed.getStarValue());
            boolean isStarSupportChanged = assessedPreviewDto.getStarSupport() != null
                    && !assessedPreviewDto.getStarSupport().equals(assessed.getStarSupport());
            boolean isStarRecommendationChanged = assessedPreviewDto.getStarRecommendation() != null
                    && !assessedPreviewDto.getStarRecommendation().equals(assessed.getStarRecommendation());
            boolean isReviewChanged = assessedPreviewDto.getReview() != null
                    && !assessedPreviewDto.getReview().equals(assessed.getReview());

            // If starts recommendaction value support or review is not null
            if (isStarValueChanged || isStarSupportChanged || isStarRecommendationChanged || isReviewChanged) {
                shopEmailRepository.findByIdShopId(shopId).forEach(shopEmail -> {
                    try {
                        internalEmailService.sendReviewEntryEmail(shopEmail.getId().getEmail(),
                                appraisalRequestRepo.findById(appraisalId).get().getCustomerName());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            if (assessedPreviewDto.getEmail_sent_time() != null) {
                assessed.setEmail_sent_time(assessedPreviewDto.getEmail_sent_time());
            }

            if (assessedPreviewDto.getSupplementary() != null) {
                assessed.setSupplementary(assessedPreviewDto.getSupplementary());
            }

            if (assessedPreviewDto.getIs_rejected_by_shop() != null
                    && assessedPreviewDto.getIs_rejected_by_shop() != assessed.isIs_rejected_by_shop()) {
                assessed.setIs_rejected_by_shop(assessedPreviewDto.getIs_rejected_by_shop());
            }

            if (assessedPreviewDto.getValue() != null) {
                assessed.setValue(assessedPreviewDto.getValue());
            }

            if (assessedPreviewDto.getStorePurchaseDateTime() != null) {
                assessed.setStorePurchaseDateTime(assessedPreviewDto.getStorePurchaseDateTime());
            }

            if (assessedPreviewDto.getFinalOffer() != null) {
                assessed.setFinalOffer(assessedPreviewDto.getFinalOffer());
            }

            if (assessedPreviewDto.getUserPurchaseDateTime() != null) {
                assessed.setUserPurchaseDateTime(assessedPreviewDto.getUserPurchaseDateTime());
            }

            if (assessedPreviewDto.getStarValue() != null) {
                assessed.setStarValue(assessedPreviewDto.getStarValue());
            }

            if (assessedPreviewDto.getStarSupport() != null) {
                assessed.setStarSupport(assessedPreviewDto.getStarSupport());
            }

            if (assessedPreviewDto.getStarRecommendation() != null) {
                assessed.setStarRecommendation(assessedPreviewDto.getStarRecommendation());
            }

            if (assessedPreviewDto.getSoldToBuyer() != null) {
                assessed.setSoldToBuyer(assessedPreviewDto.getSoldToBuyer());
            }

            if (assessedPreviewDto.getReview() != null) {
                assessed.setReview(assessedPreviewDto.getReview());
            }

            if (assessedPreviewDto.getReasonForEditPriceBuyer() != null) {
                assessed.setReasonForEditPriceBuyer(assessedPreviewDto.getReasonForEditPriceBuyer());
            }

            if (assessedPreviewDto.getReasonForEditPriceSeller() != null) {
                assessed.setReasonForEditPriceSeller(assessedPreviewDto.getReasonForEditPriceSeller());
            }

            if (assessedPreviewDto.getAdminPurchasePrice() != null) {
                assessed.setAdminPurchasePrice(assessedPreviewDto.getAdminPurchasePrice());
            }

            if (assessedPreviewDto.getAdminPurchaseDateTime() != null) {
                assessed.setAdminPurchaseDateTime(assessedPreviewDto.getAdminPurchaseDateTime());
            }

            if (assessedPreviewDto.getAdminEditReason() != null) {
                assessed.setAdminEditReason(assessedPreviewDto.getAdminEditReason());
            }

            if (assessedPreviewDto.getSendToAISystem() != null) {
                assessed.setSendToAISystem(assessedPreviewDto.getSendToAISystem());
            }

            assessedAppraisalService.updateAssessed(assessed);

            return new ResponseEntity<>(getAssessedById(appraisalId, shopId, null).getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }

    private int userIdtoShopId(int userId) {
        return Math.toIntExact(shopInvoiceRepo.getShopIdsByUserID(Long.valueOf(userId)).get(0));
    }

    private AssessedPreviewDto convertToDto(Assessed assessed) {
        AssessedPreviewDto dto = new AssessedPreviewDto();
        dto.setAppraisalid(assessed.getId().getAppraisalid());
        dto.setShopid(assessed.getId().getShopid());
        dto.setEmail_sent_time(assessed.getEmail_sent_time());
        dto.setDraft_email_sent_time(assessed.getDraft_email_sent_time());
        dto.setIs_rejected_by_shop(assessed.isIs_rejected_by_shop());
        dto.setAssessedEx(assessed.getAssessed_ex());
        dto.setUpdatedAt(assessed.getUpdatedAt());
        dto.setStatus(assessed.getStatus());
        dto.setValue(assessed.getValue());
        dto.setSupplementary(assessed.getSupplementary());
        dto.setFinalOffer(assessed.getFinalOffer());
        dto.setStarValue(assessed.getStarValue());
        dto.setStarSupport(assessed.getStarSupport());
        dto.setStarRecommendation(assessed.getStarRecommendation());
        dto.setSoldToBuyer(assessed.getSoldToBuyer());
        dto.setReview(assessed.getReview());
        dto.setReasonForEditPriceBuyer(assessed.getReasonForEditPriceBuyer());
        dto.setReasonForEditPriceSeller(assessed.getReasonForEditPriceSeller());
        dto.setAdminEditReason(assessed.getAdminEditReason());
        dto.setAdminPurchaseDateTime(assessed.getAdminPurchaseDateTime());
        dto.setAdminPurchasePrice(assessed.getAdminPurchasePrice());
        dto.setSendToAISystem(assessed.getSendToAISystem());
        dto.setUserPurchaseDateTime(assessed.getUserPurchaseDateTime());
        dto.setStorePurchaseDateTime(assessed.getStorePurchaseDateTime());
        dto.setAdminPrices(null);
        dto.setMemos(null);

        return dto;
    }
}
