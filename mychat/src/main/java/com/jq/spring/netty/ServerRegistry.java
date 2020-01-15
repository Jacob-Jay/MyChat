package com.jq.spring.netty;

import com.jq.netty.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * registry netty server in ioc
 *
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-15 16:31
 */
@Component
@PropertySource("classpath:/netty.properties")
public class ServerRegistry implements InitializingBean,DisposableBean{

    @Value("${server.port}")
    private int port;

    private Server server;

    @Override
    public void afterPropertiesSet() throws Exception {
        server = Server.getInstance(port);
        server.start();
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
    }
}
