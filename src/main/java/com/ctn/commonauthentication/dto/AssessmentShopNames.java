package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.ShopID;

import java.util.List;

public class AssessmentShopNames {

    private List<ShopTitle> shops;

    private AssessmentShopNames() {}

    public List<ShopID> ids() {
        return this.shops.stream().map(e -> e.id()).toList();
    }
}
