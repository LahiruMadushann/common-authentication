package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.ShopTitle;

import java.util.List;

public class AssessmentShopNames {

    private List<ShopTitle> shops;

    private AssessmentShopNames() {}

    public List<ShopID> ids() {
        return this.shops.stream().map(e -> e.id()).toList();
    }
}
