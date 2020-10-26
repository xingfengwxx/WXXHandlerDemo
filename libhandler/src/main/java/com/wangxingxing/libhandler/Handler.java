package com.wangxingxing.libhandler;

/**
 * author : 王星星
 * date : 2020/10/26 23:18
 * email : 1099420259@qq.com
 * description :
 */
public class Handler {

    private Looper mLooper;
    //消息队列
    private MessageQueue mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    /**
     * 发送消息
     * @param msg
     */
    public final void sendMessage(Message msg) {
        msg.target = this; //精妙之处，值得回味
        mQueue.enqueueMessage(msg);
    }

    /**
     * 转发消息
     * @param msg
     */
    public void dispatchMessage(Message msg) {
        handlerMessage(msg);
    }

    /**
     * 处理消息
     * @param msg
     */
    public void handlerMessage(Message msg) {

    }
}
