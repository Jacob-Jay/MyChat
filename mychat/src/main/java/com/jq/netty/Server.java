package com.jq.netty;

import com.jq.netty.handler.HandlerPool;
import com.jq.protocol.marshaller.MarDecoder;
import com.jq.protocol.marshaller.MarEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * init and start netty serverBootstrap
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 21:49
 */
@Component
@PropertySource("classpath:/netty.properties")
public class Server implements InitializingBean,DisposableBean {

    @Value("${server.port}")
    private int port;


    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;




    private boolean start = false;
    private static Server server;

    @Autowired
    private HandlerPool handlerPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        server = Server.getInstance(port);
        server.start();
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
    }



    public static Server getInstance(int port) {
        if (server == null) {
            synchronized (Server.class) {
                if (server == null) {
                    server = new Server();
                }
            }
        }
        return server;
    }

    private Server( ) {
        if (port == 0) {
            port = 8899;
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
                            pipeline.addLast(new NettyInHandler(handlerPool));
                        }
                    }).channel(NioServerSocketChannel.class);



    }

    /**
     * start server
     */
    private void start() {

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
    private void stop() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }




}
