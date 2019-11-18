package gordon.study.concurrent.basic;

public class ThreadMemberTest {

    public static void main(String[] args) {
        ThreadMemberTest instance = new ThreadMemberTest();
        instance.work();
    }

    private void work() {
        new TestThread().start();
        new TestThread().start();
        new TestThread().start();
    }

    private class TestThread extends Thread {

        private int count;

        @Override
        public void run() {
            System.out.println(++count);
        }
    }
}

/*
显然，这是在测试每个线程对象的成员变量是独立的。

废话，线程对象本身是一个普通的Java对象，分配在堆区，没啥特别的。
 */