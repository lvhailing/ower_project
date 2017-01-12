package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

/**
 * 资产--消息列表（1-2）用于接收后台返回的json数据
 */
public class ResultMessageListBean implements IMouldType {
    private String status; //消息状态(read:已读;unread:未读)
    private String title;//消息标题
    private String content;//消息内容
    private String sendTime;//发送时间
    private String messageId;//消息编号

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}