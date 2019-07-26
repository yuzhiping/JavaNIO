package com.github.hexsmith.bio.pseudo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 时间服务器处理类的线程池
 *
 * @author yuzp17311
 * @date 2016/3/28
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    public void execute(java.lang.Runnable task) {
        executorService.execute(task);
    }
}
