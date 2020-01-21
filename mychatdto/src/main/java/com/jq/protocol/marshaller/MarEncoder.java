package com.jq.protocol.marshaller;

import com.jq.dto.marshaller.MarHead;
import com.jq.dto.marshaller.MarMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义协议 marshalling编码器
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-19 11:43
 */
public class MarEncoder extends MessageToByteEncoder<MarMessage>{

    private MarshalingEncoder marshalingEncoder;

    public MarEncoder () throws IOException {
        this.marshalingEncoder = new MarshalingEncoder();
    }

    public void encode(ChannelHandlerContext ctx, MarMessage msg, ByteBuf out) throws Exception {
        encode(msg, out);
    }

    private void encode(MarMessage msg, ByteBuf out) throws Exception {
            MarMessage marMessage = (MarMessage) msg;
            MarHead head = marMessage.getHead();
            head.check();
            out.writeInt(head.getVersion());
            out.writeBytes(MarHead.LENGTH_CONSTANT);
            out.writeInt(head.getAction());
//            out.writeInt(head.getMessageScope());
//            out.writeInt(head.getContactId());
            out.writeInt(head.getAccountNum());
//            out.writeInt(head.getMessageType());
            Map<String, Object> attachment = head.getAttachment();
            if (attachment == null || attachment.size() == 0) {
                out.writeInt(0);
            } else {
                out.writeInt(attachment.size());
                byte[] keybytes = null;

                for (Map.Entry<String, Object> entry : attachment.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    keybytes = key.getBytes(CharsetUtil.UTF_8);
                    out.writeInt(keybytes.length);
                    out.writeBytes(keybytes);
                    marshalingEncoder.encode(value,out);
                }
            }
            Object body = marMessage.getBody();
            if (body != null) {
                marshalingEncoder.encode(body,out);
            }
            out.setInt(4, out.readableBytes() - 8);
    }
}
