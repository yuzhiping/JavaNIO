package com.github.hexsmith.heartbeat;

import java.util.Random;

/**
 * Retry policy that retries a set number of times with increasing sleep time between retries
 *
 * @author hexsmith
 * @version v1.0
 * @since 2020-12-26 23:15
 */
public class ExponentialBackOffRetry implements RetryPolicy {

    private static final int MAX_RETRIES_LIMIT = 29;
    private static final int DEFAULT_MAX_SLEEP_MS = Integer.MAX_VALUE;

    private final Random random = new Random();
    private final long baseSleepTimeMs;
    private final int maxRetries;
    private final int maxSleepMs;

    public ExponentialBackOffRetry(int baseSleepTimeMs, int maxRetries) {
        this(baseSleepTimeMs, maxRetries, DEFAULT_MAX_SLEEP_MS);
    }

    public ExponentialBackOffRetry(int baseSleepTimeMs, int maxRetries, int maxSleepMs) {
        this.maxRetries = maxRetries;
        this.baseSleepTimeMs = baseSleepTimeMs;
        this.maxSleepMs = maxSleepMs;
    }

    @Override
    public boolean allowRetry(int retryCount) {
        return retryCount < maxRetries;
    }

    @Override
    public long getSleepTimeMs(int retryCount) {
        if (retryCount < 0) {
            throw new IllegalArgumentException("retries count must greater than 0.");
        }
        if (retryCount > MAX_RETRIES_LIMIT) {
            System.out
                .println(String.format("maxRetries too large (%d). Pinning to %d", maxRetries, MAX_RETRIES_LIMIT));
            retryCount = MAX_RETRIES_LIMIT;
        }
        long sleepMs = baseSleepTimeMs * Math.max(1, random.nextInt(1 << retryCount));
        if (sleepMs > maxSleepMs) {
            System.out.printf("Sleep extension too large (%d). Pinning to %d%n", sleepMs, maxSleepMs);
            sleepMs = maxSleepMs;
        }
        return sleepMs;
    }
}