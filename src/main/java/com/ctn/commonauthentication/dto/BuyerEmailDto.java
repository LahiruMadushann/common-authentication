package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.ShopConditionEmails;
import com.ctn.commonauthentication.entity.ShopConditionEmailsKey;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class BuyerEmailDto {

    Integer order;
    @Email
    String email;

    public ShopConditionEmails toShopConditionEmails(Integer shopId) {
        ShopConditionEmails shopConditionEmails = new ShopConditionEmails();
        shopConditionEmails.setEmailOrder(order);
        shopConditionEmails.setId(new ShopConditionEmailsKey(shopId, email));
        return shopConditionEmails;
    }
}
