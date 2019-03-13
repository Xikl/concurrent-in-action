package com.ximo.efc.effectivejava.chap10.tip67;


import com.ximo.efc.effectivejava.chap04.tip18.ForwardingSet;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author 朱文赵
 * @date 2019/3/13 16:43
 */
public class ObservableSet<E> extends ForwardingSet<E> {

    @FunctionalInterface
    private interface SetObserver<E> {
        /**
         * 添加元素
         *
         * @param set
         * @param element
         */
        void added(ObservableSet<E> set, E element);
    }

    public ObservableSet(Set<E> set) {
        super(set);
    }

    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    private void notifyElementAdded(E element) {
        synchronized (observers) {
            for (SetObserver<E> observer : observers) {
                observer.added(this, element);
            }
        }
    }

    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added) {
            notifyElementAdded(element);
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c) {
            result |= add(element);
        }
        return result;
    }

    public static void main(String[] args) {
//        testObserverWhenError();
        testObserverBySubThread();
    }

    public static void testObsever() {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        // 创建observer实例
        SetObserver<Integer> setObserver = (s, e) -> System.out.println(e);


        set.addObserver(setObserver);
        // 遍历添加
        IntStream.rangeClosed(0, 100).forEach(set::add);
    }


    /**
     * 他会打印1 - 23的数字 然后抛出异常
     * 因为一个正在遍历的集合 然后 它删除了
     * Exception in thread "main" java.util.ConcurrentModificationException
     */
    private static void testObserverWhenError() {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    set.removeObserver(this);
                }
            }
        });

        // 遍历添加
        IntStream.rangeClosed(0, 100).forEach(set::add);
    }


    private static void testObserverBySubThread() {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        // 由于主线程占有 observers这个锁 然后子线程 会一直得到不该锁 主线程也一直再等他 所以会导致死锁
        // 解决办法就是加上超时时间
        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    try {
                        executorService.submit(() -> set.removeObserver(this)).get(3, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }finally {
                        // 关闭线程池
                        executorService.shutdown();
                    }
                }
            }
        });

        // 遍历添加
        IntStream.rangeClosed(0, 100).forEach(set::add);
    }

}