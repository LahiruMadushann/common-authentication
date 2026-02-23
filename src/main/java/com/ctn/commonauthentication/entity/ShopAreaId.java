package com.ctn.commonauthentication.entity;

import java.io.Serializable;
import java.util.Objects;

public class ShopAreaId implements Serializable {
    private Integer areaId;
    private Integer shopId;

    public ShopAreaId() {
    }

    public ShopAreaId(Integer areaId, Integer shopId) {
        this.areaId = areaId;
        this.shopId = shopId;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopAreaId)) return false;
        ShopAreaId that = (ShopAreaId) o;
        return Objects.equals(areaId, that.areaId) &&
                Objects.equals(shopId, that.shopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaId, shopId);
    }
}
