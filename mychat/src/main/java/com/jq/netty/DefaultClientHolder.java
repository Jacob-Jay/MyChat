package com.jq.netty;

import com.jq.exception.ClientRepeatException;
import io.netty.channel.Channel;

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


    public void add(Channel channel) throws ClientRepeatException {
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        String hostName = socketAddress.getHostName();
        if (clients.containsKey(hostName)) {
            throw new ClientRepeatException();
        }
        clients.put(hostName, channel);
    }

    public void remove(Channel channel) {

    }
}
