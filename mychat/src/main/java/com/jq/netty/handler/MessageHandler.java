package com.jq.netty.handler;

import com.jq.exception.ClientRepeatException;
import com.jq.netty.ClientHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 21:56
 */
public class MessageHandler extends SimpleChannelInboundHandler<Object> {

    private ClientHolder clientHolder;

    public MessageHandler(ClientHolder clientHolder) {
        this.clientHolder = clientHolder;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

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
