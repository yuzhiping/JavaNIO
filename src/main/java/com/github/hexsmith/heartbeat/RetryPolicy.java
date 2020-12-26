package com.github.hexsmith.heartbeat;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2020-12-26 23:14
 */
public interface RetryPolicy {

    /**
     * Called when an operation has failed for some reason. This method should return
     * true to make another attempt.
     *
     * @param retryCount the number of times retried so far (0 the first time)
     * @return true/false
     */
    boolean allowRetry(int retryCount);

    /**
     * get sleep time in ms of current retry count.
     *
     * @param retryCount current retry count
     * @return the time to sleep
     */
    long getSleepTimeMs(int retryCount);

}
