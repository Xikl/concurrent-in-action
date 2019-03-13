package com.ximo.efc.effectivejava.chap10.tip67;


import com.ximo.efc.effectivejava.chap04.tip18.ForwardingSet;

import java.util.*;
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


        // 创建observer实例
        SetObserver<Integer> setObserver = (s, e) -> System.out.println(e);


        set.addObserver(setObserver);
        // 遍历添加
        IntStream.rangeClosed(0, 100).forEach(set::add);
    }


}