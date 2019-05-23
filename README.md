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
- 锁粗化
```java
for(){
    // 每次循环都需要获得锁
    synchronized(object){
        
    }
}
锁粗话后 应该变成一个循环值获得一个锁
synchronized(object){
    for(){
        // do something
    }
}
```
### jvm中的锁优化
- `偏向锁`
如果一个线程重复请求一个锁，就会进入偏向模式。即一旦获得了锁之后，下次再进入后
则无需同步操作
- `轻量级锁`
将对象的头部指向持有锁的堆栈的内部，来判断一个锁中是否持有轻量级锁，
如果获得轻量锁，则进入临界区，如果未膨胀为重量级锁？？
- `自旋锁`
如果一个线程没有获得锁，系统会假设该线程在几个CPU时钟周期后能够锁
所以会让其空走几个循环（自旋的由来），如果可以获得锁，那么就顺利进入临界区，
如果还未获得锁，才会真正的将其挂起（操作系统中）
- `锁消除` 
通过配置-XX:DoEscapeAnalysis开启逃逸分析
所谓锁消除就是当一个变量如（StringBuffer Vector）等java api
在线程栈上创建的变量，且不会逃逸，那么系统就会对其进行锁消除

### 对于unsafe的理解
具体查看 thinkingandprogramming\stage3\atomic\UnsafeApp.java
```java
        // 愚蠢的counter
        //  22:47:19.679 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 995793, 耗时:73
        execute(new StupidCounter());

        // sync
        // 22:50:30.792 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:64
        execute(new SyncCounter());

        // 22:56:34.370 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:60
        execute(new LockCounter());

        // 22:59:02.303 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:53
        execute(new AtomicCounter());

        // 23:08:51.663 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:81
        execute(new MyCasCounter());

```

