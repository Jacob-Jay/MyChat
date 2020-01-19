package com.jq.protocol.marshaller;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2019-12-20 11:48
 */
public class MarshalingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    private final Marshaller marshaller;


    public MarshalingEncoder( ) throws IOException {

        this.marshaller = MarshallingFactory.buildMarshaller();
    }

    public void encode(  Object msg, ByteBuf out) throws Exception {
        int lengthPos = out.writerIndex();
        out.writeBytes(LENGTH_PLACEHOLDER);
        marshaller.start(new ChannelBufferByteOutput(out));
        marshaller.writeObject(msg);
        marshaller.finish();
        marshaller.close();

        out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
    }
}

