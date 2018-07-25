package com.ximo.thread.design.pattern.chap10.thread.specific;

/**
 * @author 朱文赵
 * @date 2018/7/20 17:14
 * @description
 */
public class ThreadPrivateMember {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new MessageTask("one message").start();
        }

        Thread.sleep(50);
    }

    private static class MessageTask extends Thread {

        private final String message;

        public MessageTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println(message);
        }
    }



}
