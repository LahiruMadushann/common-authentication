package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.ShopConditionEmails;
import com.ctn.commonauthentication.entity.ShopConditionEmailsKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShopConditionEmailsRepository extends JpaRepository<ShopConditionEmails, ShopConditionEmailsKey> {
    List<ShopConditionEmails> findByIdShopId(Integer shopId);
}
