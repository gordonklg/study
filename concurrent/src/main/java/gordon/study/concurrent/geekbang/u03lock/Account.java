package gordon.study.concurrent.geekbang.u03lock;

public class Account {

    private int balance;

    public Account(int initBalance) {
        this.balance = initBalance;
    }

    // 转账
    private void transfer(Account target, int amt) {
        synchronized (Account.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(100000);
        Account b = new Account(100000);
        Account c = new Account(100000);
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
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(b.balance);
    }
}