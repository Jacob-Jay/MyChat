package com.jq.netty;

import com.jq.netty.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 21:49
 */
public class Server {

    private int port = 8899;

    ClientHolder clientHolder = new DefaultClientHolder();


    public void init() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss,worker)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MessageHandler(clientHolder));
                        }
                    }).channel(NioServerSocketChannel.class);

            ChannelFuture bindFuture = server.bind(port).sync();
            bindFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new Server().init();
    }

}
