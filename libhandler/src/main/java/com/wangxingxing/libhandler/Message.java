package com.wangxingxing.libhandler;

/**
 * author : 王星星
 * date : 2020/10/26 23:18
 * email : 1099420259@qq.com
 * description :
 */
public class Message {

    public Handler target;
    public int what;
    public Object obj;

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "target=" + target +
                ", what=" + what +
                ", obj=" + obj +
                '}';
    }
}
