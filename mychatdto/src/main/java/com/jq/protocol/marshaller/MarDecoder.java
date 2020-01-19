package com.jq.protocol.marshaller;

import com.jq.dto.marshaller.MarHead;
import com.jq.dto.marshaller.MarMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 自定义协议 marshalling解码器
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-19 11:43
 */
public class MarDecoder extends LengthFieldBasedFrameDecoder{

    private MarshallingDecoder marshallingDecoder;


    public MarDecoder(int maxFrameLength) throws IOException {
        super(maxFrameLength, 4,4,0,0 );
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decode = super.decode(ctx, in);
        if (decode == null) {
            return null;
        }
        ByteBuf frame = (ByteBuf) decode;
        return getMarMessage(frame);
    }

    private MarMessage getMarMessage(ByteBuf frame) throws Exception {
        MarMessage message = new MarMessage();
        MarHead head = new MarHead();
        head.setVersion(frame.readInt());
        head.setLength(frame.readInt());
        head.setMessageScope(frame.readInt());
        head.setContactId(frame.readInt());
        head.setMessageType(frame.readInt());
        int attachSize = frame.readInt();
        if (attachSize != 0) {
            Map<String,Object> attachment = new HashMap<>(attachSize);
            for (int index = 0;index<attachSize;index++) {
                int keyLength = frame.readInt();
                String key = frame.readBytes(keyLength).toString(CharsetUtil.UTF_8);
                Object value = marshallingDecoder.decode(frame);
                attachment.put(key, value);
            }
            head.setAttachment(attachment);
        }


        message.setHead(head);
        if (frame.isReadable()) {
            Object body = marshallingDecoder.decode(frame);
            message.setBody(body);
        }
        return message;
    }
}
