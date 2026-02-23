package com.ctn.commonauthentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "apis.ai-final-offer")
@Data
public class AIMatchingFinalOfferConfig {
    private String url;
    private String token;
}
