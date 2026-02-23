package com.ctn.commonauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "appraisal_request")
@Data
public class AppraisalRequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appraisalid")
    private Long appraisalId;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "note")
    private String note;

    @Column(name = "message_for_matching_shop")
    private String messageForMatchingShop;

    @Column(name = "ip")
    private String ip;

    @Column(name = "param")
    private String param;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_post_number")
    private String customerPostNumber;

    @Column(name = "customer_prefecture")
    private String customerPrefecture;

    @Column(name = "customer_municipalities")
    private String customerMunicipalities;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_address_detail")
    private String customerAddressDetail;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "car_maker")
    private String carMaker;

    @Column(name = "car_model_year")
    private String carModelYear;

    @Column(name = "car_traveled_distance")
    private String carTraveledDistance;

    @Column(name = "inspect_remain")
    private String inspectRemain;

    @Column(name = "car_state")
    private String carState;

    @Column(name = "runnable")
    private String runnable;

    @Column(name = "wheel_drive")
    private String wheelDrive;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "shift")
    private String shift;

    @Column(name = "accident")
    private String accident;

    @Column(name = "displacement")
    private String displacement;

    @Column(name = "body_color")
    private String bodyColor;

    @Column(name = "loan")
    private String loan;

    @Column(name = "desire_date")
    private String desireDate;

    @Column(name = "grade")
    private String grade;

    @Column(name = "exterior")
    private String exterior;

    @Column(name = "scratch")
    private String scratch;

    @Column(name = "dent")
    private String dent;

    @Column(name = "peel")
    private String peel;

    @Column(name = "rust")
    private String rust;

    @Column(name = "interior")
    private String interior;

    @Column(name = "dirt")
    private String dirt;

    @Column(name = "tear")
    private String tear;

    @Column(name = "air_conditioning")
    private String airConditioning;

    @Column(name = "smoke")
    private String smoke;

    @Column(name = "navigation")
    private String navigation;

    @Column(name = "auto_slide")
    private String autoSlide;

    @Column(name = "leather_sheet")
    private String leatherSheet;

    @Column(name = "handle_type")
    private String handleType;

    @Column(name = "back_monitor")
    private String backMonitor;

    @Column(name = "sunroof")
    private String sunroof;

    @Column(name = "wheel")
    private String wheel;

    @Column(name = "assessment_date_first")
    private String assessmentDateFirst;

    @Column(name = "assessment_date_second")
    private String assessmentDateSecond;

    @Column(name = "assessment_date_third")
    private String assessmentDateThird;

    @Column(name = "request_ymd")
    private String requestYmd;

    @Column(name = "craeted_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "version")
    private Integer version;

    @Column(name = "saved_parameter_function")
    private String savedParameterFunction;

    @Column(name = "saved_utm_param")
    private String savedUtmParam;

    @Column(name = "is_test_data")
    private Boolean isTestData;

    @Column(name = "test_email_sent")
    private Boolean testEmailSent;

    @Column(name = "fpc")
    private String fpc;

    @Column(name = "photo_before")
    private String photoBefore;

    @Column(name = "photo_after")
    private String photoAfter;

    @Column(name = "inspection_cert_photo")
    private String inspectionCertPhoto;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraisalid", referencedColumnName = "appraisalid", insertable = false, updatable = false)
    private List<AssessmentDateModel> assessmentDates;

}
