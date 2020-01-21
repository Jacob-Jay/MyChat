package com.jq.netty.handler;

import com.jq.netty.WrapMessage;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-20 17:20
 */
public interface MessageHandler {

    void handle(WrapMessage taskNode);
}
