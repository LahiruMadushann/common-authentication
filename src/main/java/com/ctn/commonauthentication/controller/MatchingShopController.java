package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.dto.UserShopAppraisalDTO;
import com.ctn.commonauthentication.service.ShopInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/matching/appraisals")
public class MatchingShopController {

    private final ShopInvoiceService shopInvoiceService;

    @GetMapping("/shops")
    public ResponseEntity<List<UserShopAppraisalDTO>> getDetails(@RequestParam("userId") Integer userId) {
        List<UserShopAppraisalDTO> details = shopInvoiceService.getDetailsByUserId(userId);
        if (details.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
