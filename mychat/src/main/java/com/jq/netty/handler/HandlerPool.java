package com.jq.netty.handler;

import com.jq.netty.WrapMessage;

import java.util.Queue;
import java.util.concurrent.*;

/**
 *
 * 消息处理
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-20 16:44
 */
public class HandlerPool {

    private static int nThread;




    static {
        nThread = Runtime.getRuntime().availableProcessors();

    }
    //todo  线程消费待优化
    private ExecutorService messagePool = Executors.newFixedThreadPool(nThread);
    private ExecutorService logOrOutPool = Executors.newFixedThreadPool(nThread);
    private boolean messageStart = false;
    private boolean loginOrOutStart = false;
    /**
     * 存放消息队列  分为真正的消息与登录登出两类
     */
    Queue<WrapMessage> messageQueue = new LinkedBlockingQueue<>();
    Queue<WrapMessage> loginOrOutQueue = new LinkedBlockingQueue<>();


    /**
     * 往发送消息队列添加待处理的消息
     * @param message 消息
     * @return
     */
    public boolean addMessage(WrapMessage message) {
        startMessagePool();
        return messageQueue.offer(message);
    }

    /**
     * 往登录登出队列添加待处理操作
     * @param loginOrOut 登录登出操作
     * @return
     */
    public boolean addLoginOrOut(WrapMessage loginOrOut) {
        startLoginOrOutPool();
        return loginOrOutQueue.offer(loginOrOut);
    }

    /**
     * 开始执行消息消费任务
     */
    private void startMessagePool() {
        if(!messageStart) {
            for(int i = 0;i<5;i++) {

            }
        }
    }

    /**
     * 开始执行登出登出消费任务
     */
    private void startLoginOrOutPool() {
        if (!loginOrOutStart) {

        }
    }

}
