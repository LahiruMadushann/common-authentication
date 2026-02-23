package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.ShopDetailsDto;
import com.ctn.commonauthentication.repository.IShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinalOfferDetailsService {

    private final IShopRepository shopRepository;

    public ShopDetailsDto ShopDetails(Integer shopId) {
        return shopRepository.findShopDetailsById(Long.valueOf(shopId));
    }
}
