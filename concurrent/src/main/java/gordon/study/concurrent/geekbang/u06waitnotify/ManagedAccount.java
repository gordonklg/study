package gordon.study.concurrent.geekbang.u06waitnotify;

public class ManagedAccount {

    private static final AccountAllocator alloctor = new AccountAllocator();

    private volatile int balance;

    public ManagedAccount(int initBalance) {
        this.balance = initBalance;
    }

    // 转账
    private void transfer(ManagedAccount target, int amt) {
        alloctor.apply(this, target);
        try {
            synchronized (this) { // 这边的锁还是要加的
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            alloctor.free(this, target);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ManagedAccount a = new ManagedAccount(100000);
        ManagedAccount b = new ManagedAccount(100000);
        ManagedAccount c = new ManagedAccount(100000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                a.transfer(b, 1);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                b.transfer(c, 2);
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                c.transfer(a, 3);
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(b.balance);
    }
}