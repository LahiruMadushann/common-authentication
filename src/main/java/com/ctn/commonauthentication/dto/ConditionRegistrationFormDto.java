package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.Speciality;
import com.ctn.commonauthentication.util.enums.SaleDateDiterminationType;
import com.ctn.commonauthentication.util.enums.StoreSubscriptionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConditionRegistrationFormDto {

    @NotNull
    private Integer shopId;

    private List<BuyerEmailDto> emails;
    private List<ShopAreaDto> shopArea;

    private Speciality.DomesticOrImport domesticOrImport;
    private Speciality.NotMove notMove;
    private List<ImmovableOkPatternDto> immovableOkPattern;
    private String yearOkFrom;
    private String yearOkTo;
    private String yearNgFrom;
    private String yearNgTo;
    private String distanceOkFrom;
    private String distanceOkTo;
    private String distanceNgFrom;
    private String distanceNgTo;
    private SaleDateDiterminationType saleDateDetermination;
    private Integer ceilCount;


    //general only
    private Integer scaleRank;

    //sp only
    private Speciality.NotSpeciality notSpeciality;
    private String specialCarType;
    private String specialCarMaker;
    private String specialCarModel;

    private List<CarAndBrandPair> okCarTypes;
    private List<String> okBodyTypes;
    private List<String> okMakes;

    private List<CarAndBrandPair> ngCarTypes;
    private List<String> ngBodyTypes;
    private List<String> ngMakes;

    private StoreSubscriptionType subscriptionType; // will be validated in backend - not required
    private boolean test = false;
    private String operator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "Schedule start date must be in the future")
    private LocalDateTime scheduledDate;


    public void setDefaultValues(){
        scaleRank = 1;
        notSpeciality = Speciality.NotSpeciality.NG;
        saleDateDetermination = SaleDateDiterminationType.OK;
    }

}
