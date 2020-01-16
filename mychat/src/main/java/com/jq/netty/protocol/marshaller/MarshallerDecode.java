package com.jq.netty.protocol.marshaller;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 *
 * marshalling 协议解码器
 *
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-16 11:08
 */
public class MarshallerDecode extends LengthFieldBasedFrameDecoder {
    public MarshallerDecode(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }
}
