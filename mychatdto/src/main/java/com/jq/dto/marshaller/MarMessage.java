package com.jq.dto.marshaller;

import lombok.Data;

/**
 *
 * 自定义协议使用marshaller编解码时的载体
 *
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-16 11:16
 */
@Data
public class MarMessage {


    /**
     * 消息head
     */
    private MarHead head;

    /**
     * 消息body
     */
    private Object body;





}
