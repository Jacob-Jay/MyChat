package com.jq.netty;

import com.jq.dto.marshaller.MarMessage;
import com.jq.exception.ClientRepeatException;
import com.jq.netty.ClientHolder;
import com.jq.netty.handler.HandlerPool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 21:56
 */
public class NettyInHandler extends SimpleChannelInboundHandler<MarMessage> {

    private ClientHolder clientHolder;
    private HandlerPool handlerPool = new HandlerPool();

    public NettyInHandler(ClientHolder clientHolder) {
        this.clientHolder = clientHolder;
    }

    protected void channelRead0(ChannelHandlerContext ctx, MarMessage msg) throws Exception {
        handlerPool.add(new TaskNode(ctx,msg));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("reg");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("unreg");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("active");
        try {
            clientHolder.add(ctx.channel());
        } catch (ClientRepeatException e) {
            e.printStackTrace();
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("inactive");
        clientHolder.remove(ctx.channel());
    }
}
