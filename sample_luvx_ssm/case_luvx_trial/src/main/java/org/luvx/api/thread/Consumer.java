package org.luvx.thread;

/** 
 * 消费者 
 * @author 百恼  2013-11-7下午12:18:50 
 * 
 */  
public class Consumer implements Runnable{  
      
    //简单的模拟，这里一个生产容器，设置成final类型的话不允许再次赋值  
    private final Container<Bread> container;  
    //生产者线程监听器  
    private final Object producerMonitor;  
    //消费者线程监听器  
    private final Object consumerMonitor;  
      
    public Consumer(Object producerMonitor,Object consumerMonitor,Container<Bread> container){  
        this.producerMonitor = producerMonitor;  
        this.consumerMonitor = consumerMonitor;  
        this.container = container;  
    }  
  
    @Override  
    public void run() {  
        while(true){  
            consume();  
        }  
    }  
      
    //消费，两把锁的问题  
    public void consume(){  
        //如果发现容器已经满了,生产者要停  
        if(container.isEmpty()){  
            //唤醒生产者  
            synchronized(producerMonitor){  
                  
                if(container.isEmpty()){  
                    producerMonitor.notify();  
                }  
            }                  
            //消费者挂起  
            synchronized(consumerMonitor){  
                try {  
                    if(container.isEmpty()){  
                        System.out.println("消费者挂起。。。");  
                        consumerMonitor.wait();                          
                    }  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
        }else{  
            //还有面包可以进行消费  
            Bread bread = container.get();  
            System.out.println("bread:"+bread);  
        }         
    }  
}  