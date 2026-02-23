package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.AssessedInvoice;
import com.ctn.commonauthentication.entity.ShopID;
import com.ctn.commonauthentication.exception.IllegalValueException;
import com.ctn.commonauthentication.util.enums.AppraisalStatus;
import com.ctn.commonauthentication.util.enums.AppraisalType;

import java.util.List;

public class OperatorAppraisal {
    private AppraisalID appraisalid;
    private AppraisalStatus status;
    private AppraisalType type;
    private Customer customer;
    private Car car;
    private AssessmentDateList aDates;
    private AppraisalSupplement supplement;
    private AssessmentShopNames shops;
    private AssessedInvoice assessed;
    private String saved_utm_param;
    private Integer totalCount = 0;

    private Boolean isTestData = null;
    private boolean test_email_sent = false;
    private String ip = "";
    private String fpc = "";

    public AppraisalID id() {
        return this.appraisalid;
    }

    public void updateID(AppraisalID id ) {
        this.appraisalid = id;
    }

    public OperatorAppraisal(){}

    public boolean alreadySaved() {
        return this.appraisalid.filled();
    }

    public void changeStatusMatched() {
        this.status = AppraisalStatus.closed;
    }

    public OperatorAppraisal(AppraisalID appraisalid, AppraisalStatus status, AppraisalType type, Customer customer,
                             Car car, AssessmentDateList aDates, AppraisalSupplement supplement ,AssessmentShopNames shops,AssessedInvoice assessed, String saved_utm_param , Boolean isTestData ,boolean test_email_sent , String ip,String fpc) {
        this.appraisalid = appraisalid;
        this.status = status;
        this.type = type;
        this.customer = customer;
        this.car = car;
        this.aDates = aDates;
        this.supplement = supplement;
        this.shops = shops;
        this.assessed = assessed;
        this.saved_utm_param= saved_utm_param;
        this.isTestData = isTestData;
        this.test_email_sent = test_email_sent;
        this.ip = ip;
        this.fpc = fpc;
    }

    public List<ShopID> matchShopID() {
        return this.shops.ids();
    }

    public District area() {
        return this.customer.area();
    }
    //add car type
    public CarType carType() {
        return this.car.type();
    }

    public Maker maker() {
        return this.car.maker();
    }

    public BodyType bodyType() {
        return this.car.bodyType();
    }

    public List<BodyType> bodyTypes() {
        return this.car.bodyTypes();
    }

    public MadeFrom madeFrom() {
        return this.car.madeFrom();
    }

    public Runnable runnable() {
        return this.car.runnable_comp();
    }

    public CTNModelYear modelYear() {
        return this.car.modelYear();
    }

    public CTNTraveledDistance carTraveledDistance() {
        return this.car.traveledDistance();
    }

    public boolean notEditable() {
        return this.status == AppraisalStatus.closed || this.status == AppraisalStatus.user_input;
    }

    public void notice(MatchedAssessmentEmailContents a) throws IllegalValueException {
        Message
                .body(null)
                .to(this.customer)
                .send();
    }

    public String getSaved_utm_param() {
        return saved_utm_param;
    }

    public void setSaved_utm_param(String saved_utm_param) {
        this.saved_utm_param = saved_utm_param;
    }


    public boolean isTest_email_sent() {
        return test_email_sent;
    }

    public void setTest_email_sent(boolean test_email_sent) {
        this.test_email_sent = test_email_sent;
    }

    public Boolean getTestData() {
        return isTestData;
    }

    public void setTestData(Boolean testData) {
        isTestData = testData;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AppraisalID getAppraisalid() {
        return appraisalid;
    }

    public void setAppraisalid(AppraisalID appraisalid) {
        this.appraisalid = appraisalid;
    }

    public AppraisalStatus getStatus() {
        return status;
    }

    public void setStatus(AppraisalStatus status) {
        this.status = status;
    }

    public AppraisalType getType() {
        return type;
    }

    public void setType(AppraisalType type) {
        this.type = type;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public AssessmentDateList getaDates() {
        return aDates;
    }

    public void setaDates(AssessmentDateList aDates) {
        this.aDates = aDates;
    }

    public AppraisalSupplement getSupplement() {
        return supplement;
    }

    public void setSupplement(AppraisalSupplement supplement) {
        this.supplement = supplement;
    }

    public AssessmentShopNames getShops() {
        return shops;
    }

    public void setShops(AssessmentShopNames shops) {
        this.shops = shops;
    }

    public AssessedInvoice getAssessed() {
        return assessed;
    }

    public void setAssessed(AssessedInvoice assessed) {
        this.assessed = assessed;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getFpc() {
        return fpc;
    }

    public void setFpc(String fpc) {
        this.fpc = fpc;
    }
}
