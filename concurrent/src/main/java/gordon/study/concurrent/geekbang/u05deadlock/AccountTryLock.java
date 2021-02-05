package gordon.study.concurrent.geekbang.u05deadlock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountTryLock {

    private int balance;

    private final Lock lock = new ReentrantLock();

    public AccountTryLock(int initBalance) {
        this.balance = initBalance;
    }

    // 转账
    private void transfer(AccountTryLock target, int amt) {
        while (true) {
            try {
                if (this.lock.tryLock(ThreadLocalRandom.current().nextInt(1000) + 500, TimeUnit.NANOSECONDS)) {
                    try {
                        if (target.lock.tryLock()) {
                            try {
                                if (this.balance > amt) {
                                    this.balance -= amt;
                                    target.balance += amt;
                                }
                                break;
                            } finally {
                                target.lock.unlock();
                            }
                        }
                    } finally {
                        this.lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountTryLock a = new AccountTryLock(100000);
        AccountTryLock b = new AccountTryLock(100000);
        AccountTryLock c = new AccountTryLock(100000);
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