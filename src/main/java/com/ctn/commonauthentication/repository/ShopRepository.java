package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.dto.DropDown;
import com.ctn.commonauthentication.entity.ShopInvoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<ShopInvoice, Integer> {
    @Query("SELECT new com.ctn.commonauthentication.dto.DropDown(" +
            "s.id, COALESCE(spec.exclusivity, gen.exclusivity), s.name) " +
            "FROM ShopInvoice s " +
            "LEFT JOIN ShopHandlingConditionSpecial spec ON s.id = spec.shopid " +
            "LEFT JOIN ShopHandlingConditionGeneral gen ON s.id = gen.shopid " +
            "WHERE s.id IN :shopids")
    List<DropDown> loadShopExclusivity(@Param("shopids") List<Integer> shopids);
}
