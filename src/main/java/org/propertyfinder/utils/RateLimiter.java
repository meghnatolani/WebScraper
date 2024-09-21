package org.propertyfinder.utils;

import java.util.concurrent.TimeUnit;

/**
 * Rate Limiter
 * Fixed number of requests per second
 * */

public class RateLimiter {
    private final long rateLimitMillis;
    private long lastRequestTime;

    public RateLimiter(int requestsPerSecond) {
        this.rateLimitMillis = TimeUnit.SECONDS.toMillis(1) / requestsPerSecond;
        this.lastRequestTime = System.currentTimeMillis();
    }

    public synchronized void processRequest() {
        try {
            long now = System.currentTimeMillis();
            long elapsed = now - lastRequestTime;
            if (elapsed < rateLimitMillis) {
                Thread.sleep(rateLimitMillis - elapsed);
            }
            lastRequestTime = System.currentTimeMillis();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
