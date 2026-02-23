package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.util.enums.ShopTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShopAppraisalDTO {
    private Integer userId;
    private String userName;
    private Integer shopId;
    private String companyName;
    private Long appraisalId;
    private String customerEmail;
    private String customerName;
    private String customerPostNumber;
    private String customerPrefecture;
    private String customerMunicipalities;
    private String customerAddress;
    private String customerAddressDetail;
    private String customerPhone;
    private LocalDateTime createdAt;
    private String carType;
    private String carMaker;
    private String carModelYear;
    private String name;
    private Boolean stop;
    private Boolean cancel;
    private String cancellationCategory;
    private String appealStatement;
    private String businessHours;
    private ShopTypeEnum shopTypeEnum;
    private Boolean holidayMatching;
    private String paymentMethod;
    private Double refferal;
    private Double introduction;
    private String phoneNumber;
    private String shopImageUrl;
    private String postalCode;
    private String prefectures;
    private String municipalities;
    private String address;
    private List<ShopVacationDto> shopVacations;
    private List<ShopHolidaysDto> shopHolidays;
    private Float value;
    private Double finalOffer;
    private Double starValue;
    private Double starSupport;
    private Double starRecommendation;
    private List<ReviewDto> reviews;

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
