/*
 *  Copyright(C) 2016-2018 The hexsmith Authors
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.github.hexsmith.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 使用{@link Semaphore}和{@link CountDownLatch}模拟并发请求
 * @author hexsmith
 * @version v1.0
 * @see Semaphore
 * @see CountDownLatch
 * @see ExecutorService
 * @since 2018/8/6 22:44
 */
public class ConcurrentCountExample {

    private static final Logger logger = Logger.getLogger("info");

    /**
     * 请求总数
     */
    private static final int CLIENT_TOTAL = 5000;

    /**
     * 同时并发执行的线程数
     */
    private static final int THREAD_TOTAL = 200;

    /**
     * 由于是多线程并行操作count，因此要使用原子操作
     */
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        // 信号量，用户控制并发线程数
        final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
        // 闭锁，实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);
        for (int i = 0;i<CLIENT_TOTAL;i++){
            executorService.execute(()->{
                try {
                    // 获取执行许可，当许可不超过200时，允许通过，否则线程阻塞等待，直到获取许可
                    semaphore.acquire();
                    add();
                    // 释放许可
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 闭锁减一
                countDownLatch.countDown();
            });
        }
        // main线程阻塞等待，知道闭锁值为0才继续向下执行
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count = " + count);

    }

    /**
     * 计数统计
     */
    private static void add(){
        count.incrementAndGet();
        logger.log(Level.INFO,"当前count = "+ count);
    }

}
