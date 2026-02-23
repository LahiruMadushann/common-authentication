package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.BuyerEmailDto;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "shop_emails")
public class ShopConditionEmails {
    @EmbeddedId
    private ShopConditionEmailsKey id;
    @Column(name = "email_order")
    private Integer emailOrder;

    public BuyerEmailDto toBuyerEmailDto() {
        BuyerEmailDto buyerEmailDto = new BuyerEmailDto();
        buyerEmailDto.setEmail(id.getEmail());
        buyerEmailDto.setOrder(emailOrder);
        return buyerEmailDto;
    }
}
