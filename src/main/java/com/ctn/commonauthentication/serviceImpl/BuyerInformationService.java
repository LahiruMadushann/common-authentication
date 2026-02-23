package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.*;
import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.repository.BranchMapRepo;
import com.ctn.commonauthentication.repository.IAssessedRepository;
import com.ctn.commonauthentication.repository.ShopInvoiceRepo;
import com.ctn.commonauthentication.service.IBuyerInfomationService;
import com.ctn.commonauthentication.util.enums.CancellationCategory;
import com.ctn.commonauthentication.util.enums.ShopExclusivity;
import com.ctn.commonauthentication.util.enums.ShopTypeEnum;
import com.ctn.commonauthentication.util.enums.StoreSubscriptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BuyerInformationService implements IBuyerInfomationService {

    private final ShopInvoiceRepo shopInvoiceRepo;
    private final BranchMapRepo branchMapRepo;
    private final IAssessedRepository assessedRepository;

    @Override
    public BuyerDetailsDto getShopDetails(int id) {

        BuyerDetailsDto buyerDetailsDto = new BuyerDetailsDto();

        List<Object> shopDetails = shopInvoiceRepo.getShopDetails(id);
        if (!shopDetails.isEmpty()) {
            for (Object shopDetail : shopDetails) {
                Object[] column = (Object[]) shopDetail;
                buyerDetailsDto.setId((Integer) column[0]);
                buyerDetailsDto.setCompanyName((String) column[1]);
                buyerDetailsDto.setName((String) column[2]);
                buyerDetailsDto.setStop((Boolean) column[3]);
                buyerDetailsDto.setCancel((Boolean) column[4]);
                buyerDetailsDto.setUsername((String) column[40]);
                // buyerDetailsDto.setAppealStatement((String) column[41]);
                // buyerDetailsDto.setBusinessHours((String) column[42]);

                if (column[8] != null){
                    buyerDetailsDto.setShopuserid((Integer) column[8]);
                }

                if (column[5] != null){
                    buyerDetailsDto.setShopTypeEnum((ShopTypeEnum.valueOf((String) column[5])));
                }

                buyerDetailsDto.setRefferal((Long) column[6]);
                buyerDetailsDto.setIntroduction((Long) column[7]);
                buyerDetailsDto.setPhoneNumber((String) column[9]);
                buyerDetailsDto.setShopImageUrl((String) column[10]);

                buyerDetailsDto.setPostalCode((String) column[19]);
                buyerDetailsDto.setPrefectures((String) column[20]);
                buyerDetailsDto.setManicipalities((String) column[21]);
                buyerDetailsDto.setAddress((String) column[22]);

                try{
                    buyerDetailsDto.setCancellationCategory(CancellationCategory.valueOf((String) column[41]));
                } catch (Exception ignored){}

                buyerDetailsDto.setAppealStatement((String) column[42]);
                buyerDetailsDto.setBusinessHours((String) column[43]);
                LocalDateTime scheduleDate = column[44] != null ? ((Timestamp) column[44]).toLocalDateTime() : null;
                buyerDetailsDto.setScheduledDate(scheduleDate);

                // store subscription type detection logic
                if (column[11] != null && column[12] != null) { //check if both general and special

                    if (column[14] != null) {
                        buyerDetailsDto.setShopExclusivity(ShopExclusivity.valueOf((String) column[14]));
                    }

                    buyerDetailsDto.setStoreSubscriptionType(StoreSubscriptionType.SPECIAL);
                    buyerDetailsDto.setHaveBothGeneralAndSpecialConditions(true);

                } else if (column[12] != null) { // Check if special

                    if (column[14] != null) {
                        buyerDetailsDto.setShopExclusivity(ShopExclusivity.valueOf((String) column[14]));
                    }

                    buyerDetailsDto.setStoreSubscriptionType(StoreSubscriptionType.SPECIAL);
                    buyerDetailsDto.setHaveBothGeneralAndSpecialConditions(false);

                } else if (column[11] != null) { // Check if general

                    if (column[13] != null) {
                        buyerDetailsDto.setShopExclusivity(ShopExclusivity.valueOf((String) column[13]));
                    }

                    buyerDetailsDto.setStoreSubscriptionType(StoreSubscriptionType.GENERAL);
                    buyerDetailsDto.setHaveBothGeneralAndSpecialConditions(false);

                }


                if (column[16] != null && column[17] != null && !column[16].equals(column[17])){
                    buyerDetailsDto.setHeadBranchId((Long) column[17]);
                }


                //buyerDetailsDto.addBuyerEmailDto((String) column[18], (Integer) column[38]);
                //buyerDetailsDto.addShopAreaDto(getShopAreaDto(column));
                buyerDetailsDto.addBillingDataInvoices(getBillingInvoiceDto(column));
                buyerDetailsDto.addShopVactions(getShopVacationDto(column));
                buyerDetailsDto.addShopHolidays(getShopHolidaysDto(column));

                buyerDetailsDto.setHolidayMatching((Boolean) column[36]);
                buyerDetailsDto.setPaymentMethod((String) column[37]);

            }

            if (buyerDetailsDto.getShopTypeEnum() == null){
                buyerDetailsDto.setShopTypeEnum(ShopTypeEnum.HEAD_BRANCH);
            }

            if (buyerDetailsDto.getShopTypeEnum().equals(ShopTypeEnum.HEAD_BRANCH)){

                if (branchMapRepo.findAllByHeadBranchId(buyerDetailsDto.getId()).size() > 1){
                    buyerDetailsDto.setDisableShopType(true);
                }

            }

            // Fetch and add review data
            List<Assessed> assessedList = assessedRepository.findByShopid(buyerDetailsDto.getId());
            double sumStarValue = 0.0;
            double sumStarRecommend = 0.0;
            double sumStarSupport = 0.0;
            int amountStarValue = 0;
            int amountStarRecommend = 0;
            int amountStarSupport = 0;

            for (Assessed assessed : assessedList) {
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setReview(assessed.getReview());
                reviewDto.setShopid(Long.valueOf(assessed.getId().getShopid()));
                reviewDto.setAppraisalid(assessed.getId().getAppraisalid());

                if (assessed.getStarValue() != null) {
                    sumStarValue += assessed.getStarValue();
                    amountStarValue++;
                    reviewDto.setStarValue(assessed.getStarValue());
                }
                if (assessed.getStarRecommendation() != null) {
                    sumStarRecommend += assessed.getStarRecommendation();
                    amountStarRecommend++;
                    reviewDto.setStarRecommendation(assessed.getStarRecommendation());
                }
                if (assessed.getStarSupport() != null) {
                    sumStarSupport += assessed.getStarSupport();
                    amountStarSupport++;
                    reviewDto.setStarSupport(assessed.getStarSupport());
                }

                if (reviewDto.getReview() != null ||
                        reviewDto.getStarSupport() != null ||
                        reviewDto.getStarRecommendation() != null ||
                        reviewDto.getStarValue() != null) {
                    buyerDetailsDto.addReview(reviewDto);
                }
            }

            buyerDetailsDto.setStarValue(amountStarValue == 0 ? 0 : sumStarValue / amountStarValue);
            buyerDetailsDto.setStarRecommendation(amountStarRecommend == 0 ? 0 : sumStarRecommend / amountStarRecommend);
            buyerDetailsDto.setStarSupport(amountStarSupport == 0 ? 0 : sumStarSupport / amountStarSupport);

            return buyerDetailsDto;

        }

        throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Shop not found");

    }

    private static BillingInvoiceDto getBillingInvoiceDto(Object[] column) {
        BillingInvoiceDto billingInvoiceDto = new BillingInvoiceDto();

        billingInvoiceDto.setId((Long) column[23]);
        billingInvoiceDto.setZipCode((String) column[24]);
        billingInvoiceDto.setBillingAddress((String) column[25]);
        billingInvoiceDto.setBillingPrefecture((String) column[26]);
        billingInvoiceDto.setBillingMuncipalities((String) column[27]);
        billingInvoiceDto.setBillingDepartment((String) column[28]);
        billingInvoiceDto.setPic((String) column[29]);

        billingInvoiceDto.setBillingEmails(getBillingEmailsDto(column));

        return billingInvoiceDto;
    }

    public static List<BillingEmailsDto> getBillingEmailsDto(Object[] column) {
        List<BillingEmailsDto> billingEmailsDtos = new ArrayList<>();
        BillingEmailsDto billingEmailsDto = new BillingEmailsDto();
        billingEmailsDto.setId((Integer) column[39]);
        billingEmailsDto.setEmail((String) column[38]);
        billingEmailsDtos.add(billingEmailsDto);
        return billingEmailsDtos;
    }

    private static ShopVacationDto getShopVacationDto(Object[] column) {
        ShopVacationDto shopVacationDto = new ShopVacationDto();
        shopVacationDto.setId((Integer) column[30]);
        shopVacationDto.setStart((Timestamp) column[31]);
        shopVacationDto.setEnd((Timestamp)  column[32]);
        return shopVacationDto;
    }

    private static ShopHolidaysDto getShopHolidaysDto(Object[] column) {
        ShopHolidaysDto shopHolidaysDto = new ShopHolidaysDto();
        shopHolidaysDto.setId((Integer) column[33]);
        shopHolidaysDto.setDay((String) column[34]);
        shopHolidaysDto.setWeek((Integer) column[35]);
        return shopHolidaysDto;
    }
}
