package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.UserShopAppraisalDTO;

import java.util.List;

public interface ShopInvoiceService {
    List<UserShopAppraisalDTO> getDetailsByUserId(Integer userId);
}
