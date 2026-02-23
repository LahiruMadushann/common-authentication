package com.ctn.commonauthentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ShopConditionEmailsKey implements Serializable {
    @Column(name = "shopid")
    private Integer shopId;
    @Column(name = "email")
    private String email;
}
