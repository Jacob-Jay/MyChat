package com.jq.netty;

import com.jq.protocol.marshaller.MarDecoder;
import com.jq.protocol.marshaller.MarEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * init and start netty serverBootstrap
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 21:49
 */

public class Server {


    private int port = 8899;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    private boolean start = false;
    private static Server server;

    private ClientHolder clientHolder = new DefaultClientHolder();



    public static Server getInstance(int port) {
        if (server == null) {
            synchronized (Server.class) {
                if (server == null) {
                    server = new Server(port);
                }
            }
        }
        return server;
    }

    private Server(int port) {
        if (port != 0) {
            this.port = port;
        }
        init();
    }


    private void init() {
         boss = new NioEventLoopGroup();
         worker = new NioEventLoopGroup();
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MarEncoder());
                            pipeline.addLast(new MarDecoder(1024*1024*10));
                            pipeline.addLast(new NettyInHandler(clientHolder));
                        }
                    }).channel(NioServerSocketChannel.class);



    }

    /**
     * start server
     */
    public void start() {

        if (start) {
            return;
        }
        try {
            ChannelFuture bindFuture = serverBootstrap.bind(port).sync().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        start = true;
                    }
                }
            });
            bindFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * stop server
     */
    public void stop() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }




}
