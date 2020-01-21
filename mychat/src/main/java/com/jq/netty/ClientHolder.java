package com.jq.netty;

import com.jq.exception.ClientRepeatException;
import io.netty.channel.Channel;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020/1/14 22:07
 */
public interface ClientHolder {

    void add(WrapMessage  wrapMessage) throws ClientRepeatException;

    void remove(WrapMessage wrapMessage);
}
