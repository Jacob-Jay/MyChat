package com.jq.netty;

import com.jq.dto.marshaller.MarMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * node contain ctx and message to add handler queue
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-20 16:48
 */
@Data
@AllArgsConstructor
public class TaskNode {
    private ChannelHandlerContext ctx;
    private MarMessage message;
}
