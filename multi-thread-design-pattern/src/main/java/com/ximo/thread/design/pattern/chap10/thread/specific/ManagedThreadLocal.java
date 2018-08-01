package com.ximo.thread.design.pattern.chap10.thread.specific;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author 朱文赵
 * @date 2018/7/31 8:53
 * @description
 */
public class ManagedThreadLocal<T> extends ThreadLocal<T> {

    private static volatile Queue<WeakReference<ManagedThreadLocal<?>>> instance =
            new ConcurrentLinkedQueue<>();

    private volatile ThreadLocal<T> threadLocal;

    private ManagedThreadLocal(final InitValueProvider<T> provider) {
        this.threadLocal = ThreadLocal.withInitial(provider::initValue);
    }

    public static <T> ManagedThreadLocal<T> newInstance(final InitValueProvider<T> provider) {
        ManagedThreadLocal<T> managedThreadLocal = new ManagedThreadLocal<>(provider);
        instance.add(new WeakReference<>(managedThreadLocal));
        return managedThreadLocal;
    }

    public static <T> ManagedThreadLocal<T> newInstance() {
        return newInstance(new ManagedThreadLocal.InitValueProvider<>());
    }

    @Override
    public T get() {
        return threadLocal.get();
    }

    @Override
    public void set(T value) {
        threadLocal.set(value);
    }

    @Override
    public void remove() {
        if (threadLocal != null) {
            threadLocal.remove();
            threadLocal = null;
        }
    }

    /**
     * 清理该类所有的ThreadLocal实例
     *
     */
    public static void removeAll() {
        WeakReference<ManagedThreadLocal<?>> managedThreadLocalWeakReference;

        ManagedThreadLocal<?> managedThreadLocal;
        //从队列头部拿出数据 判断是否为空
        while ((managedThreadLocalWeakReference = instance.poll()) != null) {

            managedThreadLocal = managedThreadLocalWeakReference.get();
            if (managedThreadLocal != null) {
                managedThreadLocal.remove();
            }
        }
    }


    public static class InitValueProvider<T> {
        /**
         * 默认值为空
         *
         * @return 父类默认为空
         */
        protected T initValue() {
            return null;
        }
    }


}
