package com.yundian.file.test;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{  
  
    protected BlockingQueue queue = null;  
  
    public Producer(BlockingQueue queue) {  
        this.queue = queue;  
    }  
  
    public void run() {  
        try {  
        	System.out.println(new Date()+"thread-"+Thread.currentThread().getId()+":put 1");
            queue.put("1");  
            Thread.sleep(1000);
            System.out.println(new Date()+"thread-"+Thread.currentThread().getId()+":put 2");
            queue.put("2");  
            Thread.sleep(1000);  
            System.out.println(new Date()+"thread-"+Thread.currentThread().getId()+":put 3");
            queue.put("3");  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  