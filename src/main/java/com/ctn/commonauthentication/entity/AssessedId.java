package com.ctn.commonauthentication.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AssessedId {

    private Long appraisalid;
    private Integer shopid;

    public Long getAppraisalid() {
        return appraisalid;
    }

    public void setAppraisalid(Long appraisalid) {
        this.appraisalid = appraisalid;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public AssessedId() {
    }

    public AssessedId(Long appraisalid, Integer shopid) {
        this.appraisalid = appraisalid;
        this.shopid = shopid;
    }

    // Implement equals and hashCode methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessedId that = (AssessedId) o;
        return appraisalid.equals(that.appraisalid) && shopid.equals(that.shopid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appraisalid, shopid);
    }
}

