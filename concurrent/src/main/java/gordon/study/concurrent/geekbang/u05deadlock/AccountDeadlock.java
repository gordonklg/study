package gordon.study.concurrent.geekbang.u05deadlock;

public class AccountDeadlock {

    private int balance;

    public AccountDeadlock(int initBalance) {
        this.balance = initBalance;
    }

    // 转账
    private void transfer(AccountDeadlock target, int amt) {
        synchronized (this) {
            synchronized (target) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountDeadlock a = new AccountDeadlock(100000);
        AccountDeadlock b = new AccountDeadlock(100000);
        AccountDeadlock c = new AccountDeadlock(100000);
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