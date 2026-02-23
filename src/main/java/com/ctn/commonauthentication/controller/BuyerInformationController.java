package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.dto.BuyerDetailsDto;
import com.ctn.commonauthentication.service.IBuyerInfomationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/shop")
public class BuyerInformationController {
    private final IBuyerInfomationService buyerInfomationService;

    @GetMapping("/detail")
    public ResponseEntity<BuyerDetailsDto> getMatchingShopDetails(@RequestParam("id") int id) {
        BuyerDetailsDto buyerDetails = buyerInfomationService.getShopDetails(id);

        if (buyerDetails != null) {
            return ResponseEntity.ok(buyerDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
