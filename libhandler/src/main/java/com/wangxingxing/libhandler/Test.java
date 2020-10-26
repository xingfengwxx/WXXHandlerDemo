package com.wangxingxing.libhandler;

public class Test {

    public static void main(String[] args) {
        Looper.prepare();

        //主线程 LKP[迪米特法则（Law of Demeter，LoD）也称为最少知识原则（Least Knowledge Principle，LKP）]
        //好的东西看起来很简单，但是背后有很多设计的
        final Handler handler = new Handler() {
            @Override
            public void handlerMessage(Message msg) {
                //接收到消息
            }
        };

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "abc";
                handler.sendMessage(msg);
            }
        }.start();

        Looper.loop();
    }
}