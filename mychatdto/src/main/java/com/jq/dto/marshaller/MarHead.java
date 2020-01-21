package com.jq.dto.marshaller;

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
    private Integer action;//操作类型
    private Integer accountNum; //自身账号

   /*   放入attachment
   private Integer contactId; //接收消息人/群组  账号
    private Integer messageScope = MessageConstant.MESSAGE_SCOPE_SINGLE;//消息范围  单聊/群聊
    private Integer messageType=MessageConstant.MESSAGE_TYPE_STRING;  //文字，图片。。。*/
    private Map<String,Object> attachment = null; //附带消息

    /**
     * 校验自身必要数据是否存在
     */
    public void check() {
        if (action == null) {
            throw new RuntimeException("必须指定操作类型");
        }
        if (accountNum == null) {
            throw new RuntimeException("必须指定自身账号");
        }

    }
}
