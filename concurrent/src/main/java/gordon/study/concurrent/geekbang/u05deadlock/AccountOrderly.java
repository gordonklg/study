package gordon.study.concurrent.geekbang.u05deadlock;

public class AccountOrderly {

    private int id;

    private volatile int balance;

    public AccountOrderly(int id, int initBalance) {
        this.id = id;
        this.balance = initBalance;
    }

    // 转账
    private void transfer(AccountOrderly target, int amt) {
        AccountOrderly left = this;
        AccountOrderly right = target;
        if(this.id > target.id) {
            left = target;
            right = this;
        }
        synchronized (left) {
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountOrderly a = new AccountOrderly(1, 100000);
        AccountOrderly b = new AccountOrderly(2, 100000);
        AccountOrderly c = new AccountOrderly(3, 100000);
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