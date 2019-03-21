# concurrent-in-action
并发学习心得
###锁的优化以及注意事项
- 要减少每个线程的锁的占有时间
对需要同步的方法进行同步，其他的则不要进行同步，如：
```java
public Matcher matcher(CharSequence input) {
        if (!compiled) {
            synchronized(this) {
                if (!compiled)
                    compile();
            }
        }
        Matcher m = new Matcher(this, input);
        return m;
    }
```
- 减小锁粒度;
java7中ConcurrentHashMap中的分段锁的设计先hash再决定锁定那个分段Segment
进而增加系统的并发能力
- 使用读写锁： ReadAndWriteLock
- 锁分离： 例如LinkedBlockingQueue中的putLock takeLock
```java
/** Lock held by take, poll, etc */
private final ReentrantLock takeLock = new ReentrantLock();

/** Wait queue for waiting takes */
private final Condition notEmpty = takeLock.newCondition();

/** Lock held by put, offer, etc */
private final ReentrantLock putLock = new ReentrantLock();

/** Wait queue for waiting puts */
private final Condition notFull = putLock.newCondition();
```



