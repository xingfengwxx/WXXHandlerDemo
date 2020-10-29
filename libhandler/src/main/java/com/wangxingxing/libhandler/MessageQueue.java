package com.wangxingxing.libhandler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author : 王星星
 * date : 2020/10/26 23:18
 * email : 1099420259@qq.com
 * description :
 */
public class MessageQueue {

    //互斥锁和条件变量
    public Lock lock;
    public Condition notEmpty;
    public Condition notFull;
    public Message[] items;

    public int putIndex;
    public int takeIndex;
    public int count;


    public MessageQueue() {
        this.items = new Message[50];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * 消息入队,生产者
     * @param message
     */
    public void enqueueMessage(Message message) {
        try {
            lock.lock();
            //队列满了，阻塞
            while (count == items.length) {
                    notFull.await();
            }
            //入队
            items[putIndex] = message;
            //循环取值
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            count++; //元素计数
            //有东西了，通知消费者消费
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 消息出队，消费者
     * @return
     */
    public Message next() {
        Message msg = null;
        try {
            lock.lock();
            while (count == 0) {
                notEmpty.await();
            }
            msg = items[takeIndex];
            //循环取值
            takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;
            count--;
            //又有新的空的位置，通知生产者生产
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return msg;
    }
}
