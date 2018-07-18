### multi-thread-design-pattern
>1.Immutable Object 模式
#### 角色
- 1.1Immutable object 负责存储一组不可变状态，不会对外暴露任何可以修改器状态的方法
（在并发编程实战中叫做逸出）
- 1.2 manipulator 负责维护各个状态的变更，然后就生成新的ImmutableObject实例
#### 条件
- 1.1 类本身采用final修饰，避免被继承
- 1.2 所有字段都是final修饰，保证无论从语义上还是JMM中都是不可变的
也就是说在线程看到final类型的变量的时，必定是加载完成的值。
- 1.3 this关键字没有暴露出去，防止其他类（如匿名内部类中）在该对象创建
过程中修改该值
- 1.4 所有字段，若引用了其他状态可变的对象(集合，数组，别的对象)，那么
必须是private的，且这些字段不能暴露出去。若真的需要返回该字段的值，请
做防御性复制，即深拷贝返回出去

> 2.Thread-specific-storage(线程特有存储模式)
#### 2.1 利用threadLocal等
> 3.ThreadPool模式
#### 3.1执行两个有依赖的任务的解决策略（防止死锁）
- 3.1.1 采用两个线程池进行提交
- 3.1.2 对线程池进行配置
    + 设置线程池的大小为一个有限制，而不是`Integer.MAX_VALUE`
    + 使用`SynchronousQueue`作为工作队列
    + 使用`CallerRunsPolicy`作为拒绝策略
```

```

