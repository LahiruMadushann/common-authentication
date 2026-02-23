package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.BillingDataInvoice;
import com.ctn.commonauthentication.entity.ShopEmails;
import com.ctn.commonauthentication.entity.ShopInvoice;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingInvoiceDto {

    private Long id;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String billingPrefecture;
    @NotBlank
    @JsonProperty("billingMuncipalities")
    private String billingMuncipalities;
    private String billingAddress;
    private String billingDepartment;
    @NotNull
    private String pic;
    private List<BillingEmailsDto> billingEmails;

    public BillingDataInvoice toBillingDataInvoice(ShopInvoice shopInvoice) {
        BillingDataInvoice billingDataInvoice = new BillingDataInvoice();
        billingDataInvoice.setZipCode(this.zipCode);
        billingDataInvoice.setBillingPrefecture(this.billingPrefecture);
        billingDataInvoice.setBillingMuncipalities(this.billingMuncipalities);
        billingDataInvoice.setBillingAddress(this.billingAddress);
        billingDataInvoice.setBillingDepartment(this.billingDepartment);
        billingDataInvoice.setPic(this.pic);
        billingDataInvoice.setShopInvoice(shopInvoice);
        return billingDataInvoice;
    }

    public List<ShopEmails> toShopEmails(int shopid) {
        List<ShopEmails> shopEmails = new ArrayList<>();
        for (BillingEmailsDto billingEmailsDto : this.billingEmails) {
            ShopEmails shopEmail = new ShopEmails();
            shopEmail.setShopId(shopid);
            shopEmail.setEmail(billingEmailsDto.getEmail());
            shopEmails.add(shopEmail);
        }
        return shopEmails;
    }
}