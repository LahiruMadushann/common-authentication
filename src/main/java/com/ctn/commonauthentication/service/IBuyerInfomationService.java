package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.BuyerDetailsDto;

public interface IBuyerInfomationService {
    BuyerDetailsDto getShopDetails(int id);
}
