package org.luvx.api.thread;

// http://blog.csdn.net/luohuacanyue/article/list/1
// http://blog.csdn.net/luohuacanyue/article/details/14648185
// http://blog.csdn.net/luohuacanyue/article/details/16359777

public class Client {  
  
    public static void main(String[] args){  
        Object producerMonitor = new Object();  
        Object consumerMonitor = new Object();  
        Container<Bread> container = new Container<Bread>(10);  
        //生产者开动  
        new Thread(new Producer(producerMonitor,consumerMonitor,container)).start();  
        new Thread(new Producer(producerMonitor,consumerMonitor,container)).start();  
        new Thread(new Producer(producerMonitor,consumerMonitor,container)).start();  
        new Thread(new Producer(producerMonitor,consumerMonitor,container)).start();  
        //消费者开动  
        new Thread(new Consumer(producerMonitor,consumerMonitor,container)).start();  
        new Thread(new Consumer(producerMonitor,consumerMonitor,container)).start();  
    }  
}  