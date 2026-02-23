package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.entity.AppraisalRequestInformation;
import com.ctn.commonauthentication.service.AppraisalRequestService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appraisal")
@Slf4j
public class AppraisalController {

    private final AppraisalRequestService appraisalRequestService;

    @GetMapping("/information")
    public ResponseEntity<Object> getAppraisalInformation(@RequestParam @NotNull Integer userId) {
        if (userId == null ) {
            return ResponseEntity.badRequest().body("User Id is required");
        }

        Optional<AppraisalRequestInformation> appraisalInformation = appraisalRequestService.getAppraisalInformation(userId);

        if (appraisalInformation.isPresent()) {
            return ResponseEntity.ok(appraisalInformation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
