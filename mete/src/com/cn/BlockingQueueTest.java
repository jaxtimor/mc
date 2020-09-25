package com.cn;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Provider implements Runnable{
    private LinkedBlockingDeque<Integer> blockingDeque;
    private volatile AtomicBoolean flag = new AtomicBoolean();
    public Provider(LinkedBlockingDeque linkedBlockingDeque){
        this.blockingDeque = linkedBlockingDeque;
        flag.set(true);
    }

    public void setFlag(boolean flag){
        this.flag.set(flag);
    }
    public void run(){
        try{
            while(flag.get()){
                boolean offer = blockingDeque.offer(1, 1, TimeUnit.SECONDS);
                System.out.println("生产者生产数据:"+offer);
                if(!offer){
                    System.out.println("生产者生产数据失败");
                    break;
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            System.out.println("生产者生产结束");
        }

    }
}

class Consumer implements Runnable{
    private LinkedBlockingDeque<Integer> blockingDeque;
    private volatile AtomicBoolean flag = new AtomicBoolean();
    public Consumer(LinkedBlockingDeque<Integer> blockingQueue ){
        this.blockingDeque = blockingQueue;
        this.flag.set(true);
    }
    public void setFlag(boolean flag){
        this.flag.set(flag);
    }
    public void run(){

        try{
            while(flag.get()){
                Integer poll = blockingDeque.poll(2, TimeUnit.SECONDS);
                System.out.println("消费者消费数据："+poll);
                if(poll == null){
                    System.out.println("消费者未取到数据");
                    break;
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("消费者消费结束");
        }

    }
}

public class BlockingQueueTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<>();
        Provider provider = new Provider(linkedBlockingDeque);
        Consumer consumer = new Consumer(linkedBlockingDeque);
        Thread thread = new Thread(provider);
        Thread thread1 = new Thread(consumer);
        thread.start();
        thread1.start();
        try {
            Thread.sleep(10000);
            provider.setFlag(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
