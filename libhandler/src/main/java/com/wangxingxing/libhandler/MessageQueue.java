package com.wangxingxing.libhandler;

/**
 * author : 王星星
 * date : 2020/10/26 23:18
 * email : 1099420259@qq.com
 * description :
 */
public class MessageQueue {

    public Message[] items;
    public int putIndex;
    public int takeIndex;

    public MessageQueue() {
        this.items = new Message[50];
    }

    /**
     * 消息入队
     * @param message
     */
    public void enqueueMessage(Message message) {
        //入队
        items[putIndex] = message;
        //循环取值
        putIndex = (++putIndex == items.length) ? 0 : putIndex;
    }

    /**
     * 消息出队
     * @return
     */
    public Message next() {
        Message msg = null;

        msg = items[takeIndex];

        //循环取值
        takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;

        return msg;
    }
}
