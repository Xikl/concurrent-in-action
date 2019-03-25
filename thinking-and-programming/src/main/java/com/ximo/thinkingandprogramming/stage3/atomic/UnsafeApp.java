package com.ximo.thinkingandprogramming.stage3.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author xikl
 * @date 2019/3/25
 */
public class UnsafeApp {

    /**
     * 不能启动
     * 补充类加载器的知识
     * Exception in thread "main" java.lang.SecurityException: Unsafe
     * 	at sun.misc.Unsafe.getUnsafe(Unsafe.java:90)
     * 	at com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp.main(UnsafeApp.java:12)
     *
     * @param args
     */
    public static void main(String[] args) {
//        Unsafe unsafe = Unsafe.getUnsafe();
        Unsafe unsafe = getUnsafe();

        System.out.println(unsafe);
    }

    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
