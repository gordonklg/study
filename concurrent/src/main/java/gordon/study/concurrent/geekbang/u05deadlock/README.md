加锁不当，可能会死锁（参考 AccountDeadlock.java）

死锁：一组互相竞争资源的线程因互相等待，导致“永久”阻塞的现象。

只有以下这四个条件都发生时才会出现死锁：
- 互斥，共享资源 X 和 Y 只能被一个线程占用；
- 占有且等待，线程 T1 已经取得共享资源 X，在等待共享资源 Y 的时候，不释放共享资源 X；
- 不可抢占，其他线程不能强行抢占线程 T1 占有的资源；
- 循环等待，线程 T1 等待线程 T2 占有的资源，线程 T2 等待线程 T1 占有的资源，就是循环等待

只要我们破坏其中一个，就可以成功避免死锁的发生：
- 破坏占用且等待条件。
一次性申请所有的资源，这样就不存在等待了。（参考 ManagedAccount.java 和 AccountAllocator.java）
- 破坏不可抢占条件。
要能够主动释放它占有的资源。这一点 synchronized 是做不到的。原因是 synchronized 申请资源的时候，如果申请不到，线程直接进入
阻塞状态了，而线程进入阻塞状态，啥都干不了，也释放不了线程已经占有的资源。在 SDK 层面解决，java.util.concurrent 包的 Lock。
（参考AccountTryLock.java）
- 破坏循环等待条件。
对资源进行排序，然后按序申请资源。（参考AccountOrderly.java）




