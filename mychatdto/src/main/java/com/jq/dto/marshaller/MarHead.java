package com.jq.dto.marshaller;

import com.jq.dto.MessageConstant;
import lombok.Data;

import java.util.Map;

/**
 * 自定义协议使用marshaller编解码时的head
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-16 11:18
 */
@Data
public class MarHead {
    public static final byte[]LENGTH_CONSTANT = new byte[4];

    private Integer version = 0x00000001;//协议版本，默认1，有配置文件配置，校验作用
    private Integer length; //消息长度
    private Integer messageScope = MessageConstant.MESSAGE_SCOPE_SINGLE;//消息范围  单聊/群聊
    private Integer contactId; //接收消息人/群组  id
    private Integer messageType=MessageConstant.MESSAGE_TYPE_STRING;  //文字，图片。。。
    private Map<String,Object> attachment = null; //附带消息

}
