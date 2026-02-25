package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.*;
import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.repository.AppraisalRequestModelRepository;
import com.ctn.commonauthentication.repository.AssessedRepository;
import com.ctn.commonauthentication.util.ColorMapper;
import com.ctn.commonauthentication.util.DesiredSaleDateMapper;
import com.ctn.commonauthentication.util.enums.Accident;
import com.ctn.commonauthentication.util.enums.RunnableStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AIFinalOfferResponseBuilder {
    private final FinalOfferDetailsService finalOfferDetailsService;
    private final AppraisalRequestModelRepository appraisalRequestModelRepository;
    private final AssessedRepository assessedRepository;
    private final AIMatchingResponseBuilder aiMatchingResponseBuilder;
    private final DesiredSaleDateMapper desiredSaleDateMapper;

    private static final ZoneOffset JAPAN_OFFSET = ZoneOffset.ofHours(9);

    public AIFinalOfferRequestDto build(Long appraisalId, Integer shopId) {
        AppraisalDetailsDto appraisalDetails = appraisalRequestModelRepository
                .findAppraisalDetailsDtoByAppraisalId(appraisalId);

        List<Assessed> assessedList = assessedRepository.findAllByAppraisalId(appraisalId, shopId);

        List<StoreFinalOffer> stores = assessedList.stream()
                .map(this::buildStore)
                .collect(Collectors.toList());

        return AIFinalOfferRequestDto.builder()
                .requestId(UUID.randomUUID().toString())
                .appraisal(buildAppraisal(appraisalDetails))
                .stores(stores)
                .build();
    }

    public StoreFinalOffer buildStore(Assessed assessed) {
        ShopDetailsDto shopDetails = finalOfferDetailsService.ShopDetails(assessed.getId().getShopid());
        FinalOfferShopDetailsDto finalOfferShopDetails = assessedRepository.findFinalOfferShopDetails(
                assessed.getId().getShopid(),
                assessed.getId().getAppraisalid());

        return StoreFinalOffer.builder()
                .storeId(shopDetails.getStoreId())
                .companyName(shopDetails.getCompanyName())
                .storeName(shopDetails.getStoreName())
                .prefecture(shopDetails.getPrefecture())
                .municipality(shopDetails.getMunicipality())
                .quotes(buildQuotes(assessed, finalOfferShopDetails))
                .review(buildReview(assessed, finalOfferShopDetails))
                .purchase(buildPurchase(assessed))
                .build();
    }

    public Quotes buildQuotes(Assessed assessed, FinalOfferShopDetailsDto finalOfferShopDetails) {
        if (assessed.getValue() == null) {
            return null;
        }

        return Quotes.builder()
                .actualPurchasePrice(assessed.getValue().longValue())
                .submittedAt(convertToOffsetDateTime(finalOfferShopDetails.getValueUpdatedAt()))
                .priority(3)
                .build();
    }

    public Review buildReview(Assessed assessed, FinalOfferShopDetailsDto finalOfferShopDetails) {
        if (assessed.getFinalOffer() == null) {
            return null;
        }

        return Review.builder()
                .finalOfferAmount(assessed.getFinalOffer().longValue())
                .submittedAt(convertToOffsetDateTime(finalOfferShopDetails.getFinalOfferUpdatedAt()))
                .priority(2)
                .build();
    }

    public Purchase buildPurchase(Assessed assessed) {
        if (assessed.getAdminPurchasePrice() == null) {
            return null;
        }

        return Purchase.builder()
                .purchaseAmount(
                        assessed.getAdminPurchasePrice() != null ? assessed.getAdminPurchasePrice().longValue() : null)
                .purchaseDate(
                        assessed.getAdminPurchaseDateTime() != null ? assessed.getAdminPurchaseDateTime().toLocalDate()
                                : null)
                .priority(1)
                .build();
    }

    public AppraisalDataFinalOffer buildAppraisal(AppraisalDetailsDto appraisalDetails) {
        String param = appraisalDetails.getParam();
        return AppraisalDataFinalOffer.builder()
                .uniqueKey("APP-" + param.substring(0, 4) + "-"
                        + String.format("%06d", Long.parseLong(param.substring(4))))
                .maker(appraisalDetails.getMaker())
                .model(appraisalDetails.getModel())
                .year(aiMatchingResponseBuilder.parseModelYear(appraisalDetails.getYear()))
                .mileage(aiMatchingResponseBuilder.parseMileage(appraisalDetails.getMileage()))
                .grade(appraisalDetails.getGrade())
                .bodyType(appraisalDetails.getBodyType())
                .color(ColorMapper.mapColor(appraisalDetails.getBodyColor()))
                .area(buildArea(appraisalDetails))
                .vehicle(buildVehicle(appraisalDetails))
                .build();
    }

    public AreaDetails buildArea(AppraisalDetailsDto appraisalDetails) {
        return AreaDetails.builder()
                .zipCode(appraisalDetails.getZipCode())
                .prefecture(appraisalDetails.getPrefecture())
                .municipality(appraisalDetails.getMunicipality())
                .build();
    }

    public VehicleInfo buildVehicle(AppraisalDetailsDto appraisalDetails) {
        String originalValue = appraisalDetails.getDesiredSaleDate();
        String mappedValue = desiredSaleDateMapper.mapToStandardValue(originalValue);

        return VehicleInfo.builder()
                .runnable(appraisalDetails.getDrivableStatus() != null
                        ? RunnableStatus.valueOf(appraisalDetails.getDrivableStatus())
                        : null)
                .accident(appraisalDetails.getAccidentHistory() != null
                        ? Accident.fromDisplayName(appraisalDetails.getAccidentHistory())
                        : null)
                .desiredSaleDate(mappedValue)
                .build();
    }

    private OffsetDateTime convertToOffsetDateTime(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().atOffset(JAPAN_OFFSET);
    }

}
