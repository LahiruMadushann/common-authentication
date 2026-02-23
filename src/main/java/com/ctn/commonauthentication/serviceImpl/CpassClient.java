package com.ctn.commonauthentication.serviceImpl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Service
public class CpassClient {
    private static final Logger log = LoggerFactory.getLogger(CpassClient.class);

    @Setter
    private String token;
    private final WebClient webClient;
    private String BASE_URL;

    public CpassClient(WebClient.Builder webClientBuilder, Environment env) {
        token = env.getProperty("apis.cpass.token");
        BASE_URL = env.getProperty("apis.cpass.url");
        log.info("Initializing CpassClient with base URL: {}", BASE_URL);
        this.webClient = webClientBuilder
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(logRequest());
                    exchangeFilterFunctions.add(logResponse());
                })
                .build();
        log.info("CpassClient initialized successfully with base URL: {}", BASE_URL);
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("================== Request Begin ==================");
            log.info("Request Method: {}", clientRequest.method());
            log.info("Request URI: {}", clientRequest.url());
            clientRequest.headers().forEach((name, values) ->
                    values.forEach(value -> log.info("Request Header: {}={}", name, value))
            );

            if (clientRequest.body() != null) {
                Mono<String> bodyMono = Mono.just(clientRequest.body().toString());
                return bodyMono.defaultIfEmpty("[Empty body]")
                        .map(body -> {
                            log.info("Request Body: {}", body);
                            log.info("================== Request End ==================");
                            return ClientRequest.from(clientRequest).build();
                        });
            }

            log.info("================== Request End ==================");
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("================== Response Begin ==================");
            log.info("Response Status: {}", clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders()
                    .forEach((name, values) -> values.forEach(value ->
                            log.info("Response Header: {}={}", name, value)));

            return clientResponse.bodyToMono(String.class)
                    .defaultIfEmpty("[Empty body]")
                    .map(body -> {
                        log.info("Response Body: {}", body);
                        log.info("================== Response End ==================");
                        // Recreate response with the consumed body
                        return ClientResponse.create(clientResponse.statusCode())
                                .headers(headers -> headers.addAll(clientResponse.headers().asHttpHeaders()))
                                .body(body)
                                .build();
                    });
        });
    }

    public Mono<SmsResponse> sendSms(SmsRequest request) {
        log.info("Sending SMS request to: {}, text length: {}",
                request.to(), request.text().length());

        return webClient.post()
                .uri("/short_messages")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SmsResponse.class)
                .doOnSuccess(response -> log.info("SMS sent successfully. Delivery Order ID: {}",
                        response.deliveryOrderId()))
                .doOnError(error -> log.error("Failed to send SMS to: {}", request.to(), error))
                .onErrorMap(this::handleError);
    }

    private Throwable handleError(Throwable error) {
        if (error instanceof WebClientResponseException wcError) {
            log.error("CPASS API error: status={}, body={}",
                    wcError.getStatusCode(), wcError.getResponseBodyAsString());

            return switch (wcError.getStatusCode().value()) {
                case 400 -> {
                    log.warn("Invalid request parameters provided");
                    yield new CpassException("Invalid request parameters", BAD_REQUEST);
                }
                case 401 -> {
                    log.error("Authentication failed - invalid API token");
                    yield new CpassException("Invalid API token", UNAUTHORIZED);
                }
                case 403 -> {
                    log.error("Authorization failed - insufficient permissions");
                    yield new CpassException("Unauthorized request", FORBIDDEN);
                }
                case 429 -> {
                    log.warn("Rate limit exceeded");
                    yield new CpassException("Request limit exceeded", TOO_MANY_REQUESTS);
                }
                default -> {
                    log.error("Unexpected CPASS API error: {}", wcError.getMessage());
                    yield new CpassException("CPASS API error: " + wcError.getMessage(),
                            HttpStatus.valueOf(wcError.getStatusCode().value()));
                }
            };
        }
        log.error("Unexpected error while calling CPASS API", error);
        return new CpassException("Unexpected error: " + error.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record SmsRequest(
            String to,
            String text,
            Boolean clickTracking,
            String userReference,
            String billSplitCode
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String to;
            private String text;
            private Boolean clickTracking;
            private String userReference;
            private String billSplitCode;

            public Builder to(String to) {
                this.to = to;
                return this;
            }

            public Builder text(String text) {
                this.text = text;
                return this;
            }

            public Builder clickTracking(Boolean clickTracking) {
                this.clickTracking = clickTracking;
                return this;
            }

            public Builder userReference(String userReference) {
                this.userReference = userReference;
                return this;
            }

            public Builder billSplitCode(String billSplitCode) {
                this.billSplitCode = billSplitCode;
                return this;
            }

            public SmsRequest build() {
                Objects.requireNonNull(to, "Phone number is required");
                Objects.requireNonNull(text, "Message text is required");
                log.debug("Building SMS request: to={}, textLength={}, clickTracking={}, " +
                                "userReference={}, billSplitCode={}",
                        to, text.length(), clickTracking, userReference, billSplitCode);
                return new SmsRequest(to, text, clickTracking, userReference, billSplitCode);
            }
        }
    }

    public record SmsResponse(
            @JsonProperty("delivery_order_id")
            Long deliveryOrderId,
            @JsonProperty("accepted_at")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            LocalDateTime acceptedAt
    ) {}

    public static class CpassException extends RuntimeException {
        private final HttpStatus status;

        public CpassException(String message, HttpStatus status) {
            super(message);
            this.status = status;
            log.error("CPASS Exception: {} (status: {})", message, status);
        }

        public HttpStatus getStatus() {
            return status;
        }
    }
}
