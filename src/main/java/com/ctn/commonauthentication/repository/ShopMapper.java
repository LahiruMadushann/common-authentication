package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.dto.CTNDateTime;
import com.ctn.commonauthentication.dto.DropDown;
import com.ctn.commonauthentication.dto.ShopTitle;
import com.ctn.commonauthentication.entity.ShopID;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {

    public List<DropDown> loadAllShopIDName();

    public List<DropDown> loadShopCandidate(@Param("shopids") List<ShopID> shopids);

    public List<DropDown> loadShopExclusivity(@Param("shopids") List<Integer> shopids);

    public List<ShopTitle> loadShopIDName();




}
