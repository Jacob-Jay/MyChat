package com.jq.dto.marshaller;

import com.jq.dto.MessageConstant;
import lombok.Data;

import java.util.Map;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-16 11:18
 */
@Data
public class MarshallingHead {

    private int version = 0x00000001;//协议版本，默认1，有配置文件配置，校验作用
    private Long length; //消息长度
    private int messageScope = MessageConstant.MESSAGE_SCOPE_SINGLE;//消息范围  单聊/群聊
    private int contactId; //接收消息人/群组  id
    private int messageType=MessageConstant.MESSAGE_TYPE_STRING;  //文字，图片。。。
    private Map<String,Object> attachment = null; //附带消息

}
