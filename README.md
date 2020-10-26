# **Handler消息机制源码分析与手写实现**

#### 示意图

![img_2.png](http://ww1.sinaimg.cn/large/0073bao7gy1gk366o02dfj30og0f7gmq.jpg)

- 从图可以看出来，MessageQueue和子线程是一对多的关系，MessageQueue和Looper是 一对一的关系 

- 那么如何做到，不同的子线程向消息队列压入消息，拿到的是同一个消息队列呢?把MessageQueue和Looper绑定好，子线程拿到Looper对象，就能拿到MessageQueue对象，什么时候去拿Looper对象？ 
- Looper跟主线程是一对一的绑定关系，想要在主线程中获取到Looper对象，那Handler的实例化就要在主线程中进行

```java
	//这里会在主线程中执行
	public Handler() {
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

	//主线程 
	//实例化Handler
 	final Handler handler = new Handler() {
            @Override
            public void handlerMessage(Message msg) {
                //接收到消息
            }
    };
```

#### 类结构示意图 

![img_3.png](http://ww1.sinaimg.cn/large/0073bao7gy1gk36gpu102j30qv0cftdx.jpg)

#### 执行流程

![img_4.png](http://ww1.sinaimg.cn/large/0073bao7gy1gk36i5yj6ij30jt0kkmyj.jpg)

- 为什么要在主线程更新UI？ 
  - 系统设计者，为了考虑到用户体验，系统性能

- 子线程要更新UI，怎么办？ 
  - 通过消息机制给主线程发送消息，让主线程更新UI，这个消息机制就是Handler 
- Handler只能用来更新UI吗？
  - Handler其实主要用来线程通信 
  - 也就是可能会出现子线程和子线程通信的情况，那么轮询器Looper初始化就有可能出现在子线程中，如何保证Looper对象能够在子线程中被正确获取，用于loop轮询呢？答案是ThreadLocal 
- ThreadLocal是什么 
  -  ThreadLocal并不是一个Thread，而是Thread的局部变量。
  -  ThreadLocal为解决多线程的并发问题而生。
  - 它使变量在每个线程中都有独立拷贝，不会出现一个线程读取变量时而被另一个程修改的现象。 
  - ![img_5.png](http://ww1.sinaimg.cn/large/0073bao7gy1gk36ojwna9j30nn0fxta0.jpg)

```java
//这两段代码，可能在主线程执行，也可能在子线程执行 
//只要这两句代码在同一个线程执行，loop执行时就能保证拿到的是prepare时同一个loop对象
Looper.prepare(); 
Looper.loop();
```

- Handler中最少知识原则的应用 

  - Handler既是消息的发送者，也是消息的处理者
  - 只通过一个Handler类就能使用背后极其复杂的消息机制 

  ```java
     /**
       * 发送消息
       * @param msg
       */
      public final void sendMessage(Message msg) {
          msg.target = this; //精妙之处，值得回味
          mQueue.enqueueMessage(msg);
      }
  ```

  