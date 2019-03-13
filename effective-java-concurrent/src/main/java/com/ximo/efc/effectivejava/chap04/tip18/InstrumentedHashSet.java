package com.ximo.efc.effectivejava.chap04.tip18;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author 朱文赵
 * @date 2019/3/13 16:38
 */
public class InstrumentedHashSet<E> extends HashSet<E> {
    // The number of attempted element insertions
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    /** 这样会导致调用addAll方法中的 会反过来调用自己的add方法 */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
