package com.jq.netty;

import com.jq.exception.ClientRepeatException;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * a container which hold client
 *
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 22:21
 */
@Component
public class DefaultClientHolder implements ClientHolder {

    /**
     * save the client connect to serverBootstrap
     * the key is client's address
     * the channel represent a client
     */
    private ConcurrentHashMap<String,Channel>clients = new ConcurrentHashMap<String, Channel>();

    /**
     * save the client last heart time
     */
    private ConcurrentHashMap<String,Long>heartTime = new ConcurrentHashMap<String, Long>();


    public void add(WrapMessage wrapMessage) throws ClientRepeatException {
        Channel channel = wrapMessage.getCtx().channel();
        String clientKey = getClientKey(channel);
        if (clients.containsKey(clientKey)) {
            throw new ClientRepeatException();
        }
        clients.put(clientKey, channel);
    }

    /**
     * get client key build up by ip+port
     * @param channel
     * @return
     */
    private String getClientKey(Channel channel) {
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        String hostName = socketAddress.getHostName();
        int port = socketAddress.getPort();
        return hostName + port;
    }

    public void remove(WrapMessage wrapMessage) {
        Channel channel = wrapMessage.getCtx().channel();
        String clientKey = getClientKey(channel);
        if (clients.contains(clientKey)) {
            clients.remove(clientKey);
            heartTime.remove(clientKey);
        }
    }
}
