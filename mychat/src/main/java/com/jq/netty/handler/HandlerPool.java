package com.jq.netty.handler;

import com.jq.netty.TaskNode;

import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-20 16:44
 */
public class HandlerPool {

    public static int nThread;

    static {
        nThread = Runtime.getRuntime().availableProcessors();

    }
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(nThread);

    Queue<TaskNode> queue = new LinkedBlockingQueue<>();

    public HandlerPool() {
        startDealTask();
    }

    private void startDealTask() {
        scheduledThreadPool.schedule(() -> {
            TaskNode taskNode = queue.poll();
            if (taskNode == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                deal(taskNode);
            }
        }, 60, TimeUnit.SECONDS);
    }

    private void deal(TaskNode taskNode) {

    }

    public void add(TaskNode taskNode) {
        queue.offer(taskNode);
    }
}
