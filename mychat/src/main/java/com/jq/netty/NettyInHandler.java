package com.jq.netty;

import com.jq.dto.MessageConstant;
import com.jq.dto.marshaller.MarMessage;
import com.jq.netty.handler.HandlerPool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 21:56
 */
public class NettyInHandler extends SimpleChannelInboundHandler<MarMessage> {


    private final HandlerPool handlerPool;

    NettyInHandler(HandlerPool handlerPool) {
        this.handlerPool = handlerPool;
    }

    protected void channelRead0(ChannelHandlerContext ctx, MarMessage msg) throws Exception {
        WrapMessage wrapMessage = new WrapMessage(ctx, msg);
        Integer action = msg.getHead().getAction();
        if (action.equals(MessageConstant.ACTION_SEND_MSG)) {
            handlerPool.addMessage(wrapMessage);
        } else{
            handlerPool.addLoginOrOut(wrapMessage);
        }
    }



    /**
     * 客户端通道关闭时，清除客户端信息缓存
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        WrapMessage wrapMessage = new WrapMessage(ctx, null);
        handlerPool.addLoginOrOut(wrapMessage);
    }
}
