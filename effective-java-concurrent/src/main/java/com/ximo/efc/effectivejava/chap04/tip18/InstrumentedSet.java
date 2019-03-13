package com.ximo.efc.effectivejava.chap04.tip18;


import java.util.Collection;
import java.util.Set;

/**
 * @author 朱文赵
 * @date 2019/3/13 16:36
 */

public class InstrumentedSet<E> extends ForwardingSet<E> {

    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}