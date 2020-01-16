package com.jq.dto.marshaller;

import lombok.Data;

/**
 *
 * marshalling 协议载体
 *
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-16 11:16
 */
@Data
public class MarshllerMessage {


    /**
     * 消息head
     */
    private MarshallingHead head;

    /**
     * 消息body
     */
    private Object body;

}
