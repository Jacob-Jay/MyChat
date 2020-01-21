package com.jq.dto;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-16 11:36
 */
public class MessageConstant {

    //登录注销消息
    public static final Integer ACTION_LOGIN = 0;  //登录
    public static final Integer ACTION_LOGPUT = 1;  //注销
    public static final Integer ACTION_SEND_MSG = 2;  //发消息



    //消息范围
    public static final Integer MESSAGE_SCOPE_SINGLE = 0;  //私聊
    public static final Integer MESSAGE_SCOPE_GROUP = 1; //群聊

    //消息类型
    public static final Integer MESSAGE_TYPE_STRING = 0;
    public static final Integer MESSAGE_TYPE_IMAGE = 1;
    public static final Integer MESSAGE_TYPE_SOUND = 2;
    public static final Integer MESSAGE_TYPE_VIDEO = 3;
    public static final Integer MESSAGE_TYPE_FILE = 4;
    public static final Integer MESSAGE_TYPE_IMOGE = 5;


    //attachmentKey
    public static final String CONTACT_NUM = "contactNum";   //接收人账号
    public static final String MESSAGE_SCOPE = "messageScope"; //消息范围
    public static final String MESSAGE_TYPE = "messageType";//消息类型

}
