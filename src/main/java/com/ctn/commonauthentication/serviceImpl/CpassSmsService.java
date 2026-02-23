package com.ctn.commonauthentication.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpassSmsService {

    private final CpassClient cpassClient;

    public void sendMessage(String phoneNumber, String message) {
        CpassClient.SmsRequest request = CpassClient.SmsRequest.builder()
                .to(phoneNumber)
                .text(message)
                .clickTracking(false)
                .userReference("optional-reference")
                .build();

        cpassClient.sendSms(request)
                .subscribe(
                        response -> log.info("SMS sent successfully. Order ID: {}", response.deliveryOrderId()),
                        error -> {
                            if (error instanceof CpassClient.CpassException) {
                                CpassClient.CpassException cpassError = (CpassClient.CpassException) error;
                                log.error("CPASS error: {} ({})", cpassError.getMessage(), cpassError.getStatus());
                            } else {
                                log.error("Unexpected error", error);
                            }
                        }
                );
    }
}
