package com.jq.protocol.marshaller;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;


/**
 * @author Jiangqing
 * @version 1.0
 * @since 2019-12-20 14:43
 */
public class MarshallingDecoder {
    private Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {
        this.unmarshaller = MarshallingFactory.unmarshaller();
    }
    public Object decode( ByteBuf in) throws Exception {
        int length = in.readInt();
        int index = in.readerIndex();
        ByteBuf slice = in.retainedSlice(index, length);

        in.readerIndex(index + length);
        ByteInput input = new ChannelBufferByteInput(slice);

        try {
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            return obj;
        } finally {
            ReferenceCountUtil.release(slice);
            unmarshaller.close();
        }
    }
}
