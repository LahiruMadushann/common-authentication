package com.ctn.commonauthentication.client;

import com.ctn.commonauthentication.config.AIMatchingFinalOfferConfig;
import com.ctn.commonauthentication.dto.AIFinalOfferRequestDto;
import com.ctn.commonauthentication.dto.AIFinalOfferResponseDto;
import com.ctn.commonauthentication.dto.APIErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AIFinalOfferClient {
    private final AIMatchingFinalOfferConfig aiMatchingFinalOfferConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AIFinalOfferResponseDto callAIFinalOfferAPI(AIFinalOfferRequestDto request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(aiMatchingFinalOfferConfig.getToken());
            headers.set("Idempotency-Key", UUID.randomUUID().toString());

            HttpEntity<AIFinalOfferRequestDto> entity = new HttpEntity<>(request, headers);

            log.info("Calling AI Final Offer API: {}", aiMatchingFinalOfferConfig.getUrl());

            try {
                String jsonPayload = objectMapper.writeValueAsString(request);
                log.debug("Request payload JSON: {}", jsonPayload);

                if (request.getAppraisal() != null && request.getAppraisal().getVehicle() != null) {
                    log.info("Sending desired_sale_date: '{}'",
                            request.getAppraisal().getVehicle().getDesiredSaleDate());
                }
            } catch (Exception e) {
                log.warn("Failed to serialize request for logging: {}", e.getMessage());
            }

            ResponseEntity<AIFinalOfferResponseDto> response = restTemplate.exchange(
                    aiMatchingFinalOfferConfig.getUrl(),
                    HttpMethod.POST,
                    entity,
                    AIFinalOfferResponseDto.class
            );

            log.info("AI Final Offer API response status: {}", response.getStatusCode());

            AIFinalOfferResponseDto responseBody = response.getBody();
            if (responseBody != null) {
                log.debug("AI Final Offer API response: {}", responseBody);
                return responseBody;
            }

            log.warn("AI Final Offer API returned null response");
            return AIFinalOfferResponseDto.builder().build();
        } catch (HttpStatusCodeException e) {
            handleHttpError(e);
            return AIFinalOfferResponseDto.builder().build();
        } catch (Exception e) {
            log.error("Error calling AI Final Offer API: {}", e.getMessage(), e);
            return AIFinalOfferResponseDto.builder().build();
        }
    }

    private void handleHttpError(HttpStatusCodeException e) {
        try {
            String errorBody = e.getResponseBodyAsString();
            log.error("AI Final Offer API error - Status: {}, Body: {}", e.getStatusCode(), errorBody);

            APIErrorResponse errorResponse = objectMapper.readValue(errorBody, APIErrorResponse.class);

            log.error("API Error Details - Code: {}, Message: {}, RequestId: {}, Details: {}",
                    errorResponse.getError().getCode(),
                    errorResponse.getError().getMessage(),
                    errorResponse.getError().getRequestId(),
                    errorResponse.getError().getDetails());

        } catch (Exception parseEx) {
            log.error("Failed to parse error response: {}", parseEx.getMessage());
        }
    }
}
