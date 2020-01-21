import com.jq.dto.MessageConstant;
import com.jq.dto.marshaller.MarHead;
import com.jq.dto.marshaller.MarMessage;
import com.jq.protocol.marshaller.MarDecoder;
import com.jq.protocol.marshaller.MarEncoder;
import com.jq.protocol.marshaller.MarshalingEncoder;
import com.jq.protocol.marshaller.MarshallingDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-19 16:03
 */
public class MarTest {

@Test
    public void t2() throws  Exception {
        ByteBufAllocator aDefault = ByteBufAllocator.DEFAULT;
        ByteBuf buffer = aDefault.buffer();
        MarshalingEncoder encoder = new MarshalingEncoder();
        encoder.encode("ss",buffer);
        encoder.encode("ss",buffer);

    MarshallingDecoder decoder = new MarshallingDecoder();
//    buffer.resetWriterIndex();
//    buffer.resetReaderIndex();
    Object decode = decoder.decode(buffer);
    Object decode1 = decoder.decode(buffer);
}


    @Test
    public  void test() throws Exception {

        MarMessage message = new MarMessage();
        MarHead head = new MarHead();

        Map<String,Object> attachment = new HashMap<>();
        attachment.put("name", "jq");
        attachment.put("age", "sad");
        T nan = new T("nan", 20);
        attachment.put("T", nan);
        head.setAttachment(attachment);
        head.setAction(MessageConstant.ACTION_LOGIN);
        head.setAccountNum(123);
        message.setHead(head);
        message.setBody(nan);
        MarEncoder encoder = new MarEncoder();

        ByteBufAllocator aDefault = ByteBufAllocator.DEFAULT;
        ByteBuf buffer = aDefault.buffer();
        encoder.encode(null,message,buffer);


        MarDecoder decoder = new MarDecoder(Integer.MAX_VALUE);
        Object decode = decoder.decode(null, buffer);
        System.out.println(decode);
    }


    @Data
    @AllArgsConstructor
    private static class T implements Serializable{
        private String sex;
        private int code;
    }
}
