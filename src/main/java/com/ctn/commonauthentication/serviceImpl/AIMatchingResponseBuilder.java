package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.*;
import com.ctn.commonauthentication.dto.Runnable;
import com.ctn.commonauthentication.repository.AppraisalRequestModelRepository;
import com.ctn.commonauthentication.util.enums.Accident;
import com.ctn.commonauthentication.util.enums.AreaType;
import com.ctn.commonauthentication.util.ColorMapper;
import com.ctn.commonauthentication.util.enums.RunnableStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AIMatchingResponseBuilder {

    private final IncreaseMatchingStoreNumber increaseMatchingStoreNumber;
    private final AppraisalRequestModelRepository appraisalRequestModelRepository;

    public AIMatchingRequestDto buildResponse(
            OperatorAppraisal appraisal,
            List<DropDown> matchingList) {

        return AIMatchingRequestDto.builder()
                .appraisalData(buildAppraisalData(appraisal))
                .matchedShops(buildMatchedShops(matchingList))
                .matchedStoreCount(matchingList.size())
                .areaType(determineAreaType(appraisal.getCustomer().getPrefecture()))
                .build();
    }

    private AppraisalData buildAppraisalData(OperatorAppraisal appraisal) {
        AppraisalDetailsDto appraisalDetails = appraisalRequestModelRepository
                .findAppraisalDetailsDtoByAppraisalId(appraisal.getAppraisalid().getContent());
        String param = appraisalDetails.getParam();
        String uniqueKey = "APP-" + param.substring(0, 4) + "-" + param.substring(4, 10);

        return AppraisalData.builder()
                .uniqueKey(uniqueKey)
                .maker(appraisal.getCar().getCar_maker())
                .model(appraisal.getCar().getCar_type())
                .year(parseModelYear(appraisal.getCar().getCar_model_year()))
                .mileage(parseMileage(appraisal.getCar().getCar_traveled_distance()))
                .grade(appraisal.getCar().getGrade())
                .bodyType(appraisal.getCar().getBody_type())
                .color(ColorMapper.mapColor(appraisal.getCar().getBody_color()))
                .vehicleInfo(buildVehicleInfo(appraisal.getCar()))
                .build();
    }

    private VehicleInfo buildVehicleInfo(Car car) {
        if (car == null) {
            return VehicleInfo.builder().build();
        }

        Runnable runnableComp = car.runnable_comp();

        return VehicleInfo.builder()
                .runnable(mapRunnable(runnableComp))
                .accident(mapAccident(runnableComp))
                .desiredSaleDate(car.getDesire_date())
                .build();
    }

    public Integer parseMileage(String mileageStr) {
        if (mileageStr == null || mileageStr.trim().isEmpty()) {
            return null;
        }

        if (mileageStr.equals("不明")){
            return null;
        }

        try {
            String cleanedStr = mileageStr
                    .replace("～", "")
                    .replace("km", "")
                    .replace(",", "")
                    .replace("、", "")
                    .replace(" ", "")
                    .trim();

            if (cleanedStr.isEmpty()) {
                return null;
            }

            return Integer.parseInt(cleanedStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private RunnableStatus mapRunnable(Runnable runnable) {
        if (runnable == null || runnable.runnable()== null) {
            return RunnableStatus.走行可;
        }

        switch (runnable.runnable()) {
            case "YES":
                return RunnableStatus.走行可;
            case "NO":
                return RunnableStatus.走行不可;
            default:
                return RunnableStatus.走行可;
        }
    }

    private Accident mapAccident(Runnable runnable) {
        if (runnable == null || runnable.getAccident() == null) {
            return Accident.なし;
        }

        switch (runnable.getAccident()) {
            case "NO":
                return Accident.なし;
            case "RESTORED":
                return Accident.あり修復済み;
            case "NOT_RESTORED":
                return Accident.あり未修理;
            case "UNKNOWN":
                return Accident.不明;
            default:
                return Accident.なし;
        }
    }

    private List<MatchedShop> buildMatchedShops(List<DropDown> matchingList) {
        if (matchingList == null || matchingList.isEmpty()) {
            return new ArrayList<>();
        }

        return matchingList.stream()
                .map(AIMatchingResponseBuilder::buildMatchedShop)
                .collect(Collectors.toList());
    }

    private static MatchedShop buildMatchedShop(DropDown dropdown) {
        return MatchedShop.builder()
                .shopId((long) dropdown.getId())
                .companyName(dropdown.getName() != null ? dropdown.getName() : "")
                .shopName(dropdown.getLabel() != null ? dropdown.getLabel() : "")
                .build();
    }

    private AreaType determineAreaType(String prefecture) {
        if (!increaseMatchingStoreNumber.increaseMatchingStoreNumber(prefecture)) {
            return AreaType.THREE_STORE;
        } else  {
            return AreaType.FOUR_STORE;
        }
    }

    public Integer parseModelYear(String modelYear) {
        if (modelYear == null || modelYear.trim().isEmpty()) {
            return null;
        }
        if (modelYear.equals("不明")){
            return null;
        }

        try {
            int startIndex = modelYear.indexOf('(');
            int endIndex = modelYear.indexOf(')');

            if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                String yearStr = modelYear.substring(startIndex + 1, endIndex).trim();
                return Integer.parseInt(yearStr);
            }

            return Integer.parseInt(modelYear.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
