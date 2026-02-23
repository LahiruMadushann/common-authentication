package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.dto.ShopDetailsDto;
import com.ctn.commonauthentication.entity.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IShopRepository extends JpaRepository<ShopModel, Long> {

    @Query("""
    SELECT new com.ctn.commonauthentication.dto.ShopDetailsDto(
        CAST(s.shopId AS integer), 
        s.companyName, 
        s.name, 
        s.prefectures, 
        s.municipalities
    ) 
    FROM ShopModel s 
    WHERE s.shopId = :shopId
    """)
    ShopDetailsDto findShopDetailsById(@Param("shopId") Long shopId);
}
