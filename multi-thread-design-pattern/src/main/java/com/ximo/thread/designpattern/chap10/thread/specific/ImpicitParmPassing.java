package com.ximo.thread.designpattern.chap10.thread.specific;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 朱文赵
 * @date 2018/7/24 12:54
 * @description 线程内部传输
 */
public class ImpicitParmPassing {

    public static void main(String[] args) throws InterruptedException {
        ClientThread clientThread;

        BusinessService businessService = new BusinessService();
        for (int i = 0; i < 4; i++) {
            clientThread = new ClientThread("sss", businessService);
            clientThread.start();
            clientThread.join();
        }
    }



}


class ClientThread extends Thread {

    private final String msg;

    private final BusinessService businessService;

    private static final AtomicInteger SEQ = new AtomicInteger(0);

    public ClientThread(String msg, BusinessService businessService) {
        this.msg = msg;
        this.businessService = businessService;
    }

    @Override
    public void run() {
        Context.INSTANCE.setId(SEQ.getAndIncrement());
        businessService.doSomething(msg);
    }
}

class Context {
    private static final ThreadLocal<Integer> INTEGER_THREAD_LOCAL = new ThreadLocal<>();

    public static final Context INSTANCE = new Context();

    private Context() {
    }

    public Integer getId() {
        return INTEGER_THREAD_LOCAL.get();
    }

    public void setId(Integer value) {
        INTEGER_THREAD_LOCAL.set(value);
    }

    public void remove() {
        INTEGER_THREAD_LOCAL.remove();
    }

}

class BusinessService {

    public void doSomething(String msg) {
        System.out.println("msg:" + msg);
        System.out.println("id:" + Context.INSTANCE.getId());
    }
}

