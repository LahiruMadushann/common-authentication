package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.util.enums.CancellationCategory;
import com.ctn.commonauthentication.util.enums.ShopExclusivity;
import com.ctn.commonauthentication.util.enums.ShopTypeEnum;
import com.ctn.commonauthentication.util.enums.StoreSubscriptionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class BuyerDetailsDto {

    //shops
    private Integer id;
    @Email
    private String username;
    private Integer shopuserid;
    private String companyName;
    private String name;
    private Boolean stop;
    private Boolean cancel;
    @JsonProperty(defaultValue = "NONE")
    private CancellationCategory cancellationCategory;
    private String appealStatement;
    private String businessHours;
    private ShopTypeEnum shopTypeEnum;
    private Boolean holidayMatching;
    private String paymentMethod;
    private Long refferal;
    private Long introduction;
    private String phoneNumber;
    private String shopImageUrl;
    private Long headBranchId;
    private String postalCode;
    private String prefectures;
    private String manicipalities;
    private String address;

    //shop_handling_condition
    private ShopExclusivity shopExclusivity;
    private StoreSubscriptionType storeSubscriptionType;

    //shop emails
    //private List<BuyerEmailDto> buyerEmailDto;

    //shop area
    //private ShopAreaDto shopAreaDto;

    //shop vacations
    private List<ShopVacationDto> shopVacations;

    //shop holidays
    private List<ShopHolidaysDto> shopHolidays;

    private List<BillingInvoiceDto> billingDataInvoices;

    private Boolean haveBothGeneralAndSpecialConditions = false;
    private Boolean disableShopType = false;

    private Double starValue;
    private Double starSupport;
    private Double starRecommendation;
    private List<ReviewDto> reviews;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "Schedule start date must be in the future")
    private LocalDateTime scheduledDate;


//    @Deprecated(forRemoval = true)
//    public void addBuyerEmailDto(String email, Integer emailOrder) {
//        BuyerEmailDto buyerEmailDto = new BuyerEmailDto();
//        buyerEmailDto.setEmail(email);
//        buyerEmailDto.setOrder(emailOrder);
//
//        //if this already contains same email, then return
//        if (this.buyerEmailDto != null) {
//            for (BuyerEmailDto buyerEmail : this.buyerEmailDto) {
//                if ((email == null) || (email.isEmpty()) || (buyerEmail != null && buyerEmail.getEmail().equals(email))) {
//                    return;
//                }
//            }
//        }
//
//
//        if (this.buyerEmailDto == null) {
//            this.buyerEmailDto = new ArrayList<>();
//        }
//
//        this.buyerEmailDto.add(buyerEmailDto);
//    }

//    public void addShopAreaDto(ShopAreaDto shopAreaDto) {
//        this.shopAreaDto = shopAreaDto;
//    }

    public void addShopVactions(ShopVacationDto shopVacationDto) {

        //if this already contains same vacation, then return
        if (this.shopVacations != null && shopVacationDto != null && shopVacationDto.getId() != null) {
            for (ShopVacationDto shopVacation : this.shopVacations) {
                if (shopVacation.getId().equals(shopVacationDto.getId())) {
                    return;
                }
            }
        }

        if (this.shopVacations == null) {
            this.shopVacations = new ArrayList<>();
        }

        this.shopVacations.add(shopVacationDto);
    }

    public void addShopHolidays(ShopHolidaysDto shopHolidaysDto) {

        //if this already contains same holiday, then return
        if (this.shopHolidays != null && shopHolidaysDto != null && shopHolidaysDto.getId() != null) {
            for (ShopHolidaysDto shopHoliday : this.shopHolidays) {
                if (shopHoliday.getId().equals(shopHolidaysDto.getId())) {
                    return;
                }
            }
        }

        if (this.shopHolidays == null) {
            this.shopHolidays = new ArrayList<>();
        }

        this.shopHolidays.add(shopHolidaysDto);
    }

    public void addBillingDataInvoices(BillingInvoiceDto billingInvoiceDto) {

        //if this already contains same invoice, then return
        if (this.billingDataInvoices != null && billingInvoiceDto != null && billingInvoiceDto.getId() != null) {
            for (BillingInvoiceDto billingInvoice : this.billingDataInvoices) {
                if (billingInvoice.getId().equals(billingInvoiceDto.getId())) {
                    return;
                }
            }
        }

        if (this.billingDataInvoices == null) {
            this.billingDataInvoices = new ArrayList<>();
        }

        this.billingDataInvoices.add(billingInvoiceDto);
    }

    public void addReview(ReviewDto reviewDto) {

        //if this already contains same review, then return
        if (this.reviews != null && reviewDto != null && reviewDto.getAppraisalid() != null) {
            for (ReviewDto review : this.reviews) {
                if (review.getAppraisalid().equals(reviewDto.getAppraisalid())) {
                    return;
                }
            }
        }

        if (this.reviews == null) {
            this.reviews = new ArrayList<>();
        }

        this.reviews.add(reviewDto);
    }

}
