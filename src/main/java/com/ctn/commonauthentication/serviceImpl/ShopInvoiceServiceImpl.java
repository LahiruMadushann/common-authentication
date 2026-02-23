package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.ReviewDto;
import com.ctn.commonauthentication.dto.ShopHolidaysDto;
import com.ctn.commonauthentication.dto.ShopVacationDto;
import com.ctn.commonauthentication.dto.UserShopAppraisalDTO;
import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.repository.AppraisalRequestRepo;
import com.ctn.commonauthentication.repository.IAssessedRepository;
import com.ctn.commonauthentication.repository.ShopInvoiceRepo;
import com.ctn.commonauthentication.service.ShopInvoiceService;
import com.ctn.commonauthentication.util.enums.ShopTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopInvoiceServiceImpl implements ShopInvoiceService {

    private final AppraisalRequestRepo appraisalRequestRepo;
    private final ShopInvoiceRepo shopInvoiceRepo;
    private final IAssessedRepository assessedRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserShopAppraisalDTO> getDetailsByUserId(Integer userId) {
        try {
            List<Long> appraisalIds = appraisalRequestRepo.findAppraisalIdByUserIdAndCustomerEmailOrderedByDate(userId);
            List<Object[]> results = shopInvoiceRepo.findDetailsByUserId(appraisalIds);
            if (results == null || results.isEmpty()) {
                return Collections.emptyList();
            }

            // Use a Map to store unique appraisals by shopId
            Map<Integer, UserShopAppraisalDTO> uniqueAppraisals = new HashMap<>();

            for (Object[] result : results) {
                Integer shopid = (Integer) result[2]; // Get shopId

                // If this appraisal doesn't exist in our map, create new DTO
                UserShopAppraisalDTO dto = uniqueAppraisals.computeIfAbsent(shopid,
                        k -> mapToUserShopAppraisalDTO(result));

                // Always try to add holiday info (duplicate holidays will be handled by Set in DTO)
                Integer hId = (Integer) result[37];
                Integer week = (Integer) result[38];
                String day = (String) result[39];
                if (week != null && day != null) {
                    ShopHolidaysDto shopHolidays = new ShopHolidaysDto();
                    shopHolidays.setId(hId);
                    shopHolidays.setWeek(week);
                    shopHolidays.setDay(day);
                    dto.addShopHolidays(shopHolidays);
                }
            }

            // add avarage of stars to each shop
            for (UserShopAppraisalDTO dto : uniqueAppraisals.values()) {

                double sumStarValue = 0.0;
                double sumStarRecommend = 0.0;
                double sumStarSupport = 0.0;

                int amountStarValue = 0;
                int amountStarRecommend = 0;
                int amountStarSupport = 0;

                for (Assessed assessed : assessedRepository.findByShopid(dto.getShopId())) {

                    ReviewDto reviewDto = new ReviewDto();
                    reviewDto.setReview(assessed.getReview());
                    reviewDto.setShopid(Long.valueOf(assessed.getId().getShopid()));
                    reviewDto.setAppraisalid(assessed.getId().getAppraisalid());

                    if (assessed.getStarValue() != null){
                        sumStarValue += assessed.getStarValue();
                        amountStarValue++;
                        reviewDto.setStarValue(assessed.getStarValue());
                    }
                    if (assessed.getStarRecommendation() != null){
                        sumStarRecommend += assessed.getStarRecommendation();
                        amountStarRecommend++;
                        reviewDto.setStarRecommendation(assessed.getStarRecommendation());
                    }
                    if (assessed.getStarSupport() != null){
                        sumStarSupport += assessed.getStarSupport();
                        amountStarSupport++;
                        reviewDto.setStarSupport(assessed.getStarSupport());
                    }

                    if (reviewDto.getReview() != null ||
                            reviewDto.getStarSupport() != null ||
                            reviewDto.getStarRecommendation() != null ||
                            reviewDto.getStarValue() != null){

                        dto.addReview(reviewDto);

                    }
                }

                dto.setStarValue(amountStarValue == 0 ? 0 : sumStarValue / amountStarValue);
                dto.setStarRecommendation(amountStarRecommend == 0 ? 0 : sumStarRecommend / amountStarRecommend);
                dto.setStarSupport(amountStarSupport == 0 ? 0 : sumStarSupport / amountStarSupport);
            }

            return new ArrayList<>(uniqueAppraisals.values());
        } catch (Exception e) {
            log.error("Error fetching details by userId: ", e);
            return Collections.emptyList();
        }
    }

    private UserShopAppraisalDTO mapToUserShopAppraisalDTO(Object[] result) {
        UserShopAppraisalDTO dto = new UserShopAppraisalDTO();
        dto.setUserId((Integer) result[0]);
        dto.setUserName((String) result[1]);
        dto.setShopId((Integer) result[2]);
        dto.setCompanyName((String) result[3]);
        dto.setAppraisalId((Long) result[4]);
        dto.setCustomerEmail((String) result[5]);
        dto.setCustomerName((String) result[6]);
        dto.setCustomerPostNumber((String) result[7]);
        dto.setCustomerPrefecture((String) result[8]);
        dto.setCustomerMunicipalities((String) result[9]);
        dto.setCustomerAddress((String) result[10]);
        dto.setCustomerAddressDetail((String) result[11]);
        dto.setCustomerPhone((String) result[12]);
        dto.setCreatedAt((LocalDateTime) result[13]);
        dto.setCarType((String) result[14]);
        dto.setCarMaker((String) result[15]);
        dto.setCarModelYear((String) result[16]);
        dto.setName((String) result[17]);
        dto.setStop((Boolean) result[18]);
        dto.setCancel((Boolean) result[19]);
        dto.setCancellationCategory((String) result[20]);
        dto.setAppealStatement((String) result[21]);
        dto.setBusinessHours((String) result[22]);
        dto.setShopTypeEnum((ShopTypeEnum) result[23]);
        dto.setHolidayMatching((Boolean) result[24]);
        dto.setPaymentMethod((String) result[25]);
        dto.setRefferal((Double) result[26]);
        dto.setIntroduction((Double) result[27]);
        dto.setPhoneNumber((String) result[28]);
        dto.setShopImageUrl((String) result[29]);
        dto.setPostalCode((String) result[30]);
        dto.setPrefectures((String) result[31]);
        dto.setMunicipalities((String) result[32]);
        dto.setAddress((String) result[33]);
        Integer vId = (Integer) result[34];
        Timestamp vacationStart = (Timestamp) result[35];
        Timestamp vacationEnd = (Timestamp) result[36];
        if (vacationStart != null && vacationEnd != null) {
            ShopVacationDto shopVacation = new ShopVacationDto();
            shopVacation.setId(vId);
            shopVacation.setStart(Timestamp.valueOf(vacationStart.toLocalDateTime()));
            shopVacation.setEnd(Timestamp.valueOf(vacationEnd.toLocalDateTime()));
            dto.addShopVactions(shopVacation);
        }
        Integer hId = (Integer) result[37];
        Integer week = (Integer) result[38];
        String day = (String) result[39];
        if (week != null && day != null) {
            ShopHolidaysDto shopHolidays = new ShopHolidaysDto();
            shopHolidays.setId(hId);
            shopHolidays.setWeek(week);
            shopHolidays.setDay(day);
            dto.addShopHolidays(shopHolidays);
        }
        dto.setValue((Float) result[40]);
        dto.setFinalOffer((Double) result[41]);

        return dto;
    }
}
