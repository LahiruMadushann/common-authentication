package com.ctn.commonauthentication.serviceImpl;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public boolean tryConsume(String key, int capacity, int refillTokens, long refillPeriodMillis) {
        TokenBucket bucket = buckets.computeIfAbsent(key,
                k -> new TokenBucket(capacity, refillTokens, refillPeriodMillis));
        return bucket.tryConsume();
    }

    private static class TokenBucket {
        private final int capacity;
        private final int refillTokens;
        private final long refillPeriodMillis;
        private int tokens;
        private long nextRefillTime;

        public TokenBucket(int capacity, int refillTokens, long refillPeriodMillis) {
            this.capacity = capacity;
            this.refillTokens = refillTokens;
            this.refillPeriodMillis = refillPeriodMillis;
            this.tokens = capacity;
            this.nextRefillTime = Instant.now().toEpochMilli() + refillPeriodMillis;
        }

        public synchronized boolean tryConsume() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = Instant.now().toEpochMilli();
            if (now >= nextRefillTime) {
                // Calculate how many periods have passed
                long periods = (now - nextRefillTime) / refillPeriodMillis + 1;
                tokens = (int) Math.min(capacity, tokens + (periods * refillTokens));
                nextRefillTime = now + refillPeriodMillis;
            }
        }
    }
}
