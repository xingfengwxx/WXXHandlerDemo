package com.wangxingxing.libhandler;

import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        Looper.prepare();

        //主线程 LKP[迪米特法则（Law of Demeter，LoD）也称为最少知识原则（Least Knowledge Principle，LKP）]
        //好的东西看起来很简单，但是背后有很多设计的
        final Handler handler = new Handler() {
            @Override
            public void handlerMessage(Message msg) {
                //接收到消息
                System.out.println(Thread.currentThread().getName() + ", received: " + msg.toString());
            }
        };

        //子线程
        for (int i = 0; i < 10; i++) {
            //多个子线程发消息
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        Message msg = new Message();
                        msg.what = i;
                        msg.obj = Thread.currentThread().getName() + ",send message:"+ UUID.randomUUID().toString();
                        System.out.println("send:"+msg);
                        handler.sendMessage(msg);
                    }
                }
            }).start();
        }

        Looper.loop();
    }
}