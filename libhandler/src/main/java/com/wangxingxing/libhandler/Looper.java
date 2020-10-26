package com.wangxingxing.libhandler;

/**
 * author : 王星星
 * date : 2020/10/26 23:18
 * email : 1099420259@qq.com
 * description :
 */
public class Looper {
    public static ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    public MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    /**
     * 初始化当前线程的Looper对象
     */
    public static void prepare() {
        //一个线程对应一个Looper对象
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    /**
     * 不断轮询队列中的消息
     */
    public static void loop() {
        Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        MessageQueue queue = me.mQueue;
        for (;;) {
            Message msg = queue.next();
            if (msg == null) {
                continue;
            }
            //转发
            msg.target.dispatchMessage(msg);
        }
    }

    /**
     * 获取当前线程的Looper对象
     * @return
     */
    public static Looper myLooper() {
        return sThreadLocal.get();
    }
}
