package org.luvx.thread;

/**
 * 生产者
 */
public class Producer implements Runnable {
    //简单的模拟，这里一个生产容器，设置成final类型的话不允许再次赋值
    private final Container<Bread> container;

    //生产者线程监听器
    private final Object producerMonitor;

    //消费者线程监听器
    private final Object consumerMonitor;

    public Producer(Object producerMonitor,Object consumerMonitor,Container<Bread> container){
        this.producerMonitor = producerMonitor;
        this.consumerMonitor = consumerMonitor;
        this.container = container;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while(true){
            produce();
        }
    }

    public void produce(){
        //这里为了形象，模拟几个制作面包的步骤
        step1();
        Bread bread = step2();
        //如果发现容器已经满了,生产者要停
        if(container.isFull()){
            //唤醒消费者
            synchronized(consumerMonitor){

                if(container.isFull()){
                    consumerMonitor.notify();
                }
            }
            //生产者挂起,两把锁的问题
            synchronized(producerMonitor){
                try {
                    if(container.isFull()){
                        System.out.println("生产者挂起...");
                        producerMonitor.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else{
            //容器中还有容量,把面包放到容器内,这里可能会有丢失
            boolean result = container.add(bread);
            System.out.println("Producer:"+result);
        }
    }

    public void step1(){}

    public Bread step2(){
        return new Bread();
    }
}