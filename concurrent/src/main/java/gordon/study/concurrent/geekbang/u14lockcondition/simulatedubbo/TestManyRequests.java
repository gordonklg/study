package gordon.study.concurrent.geekbang.u14lockcondition.simulatedubbo;

import java.util.concurrent.TimeoutException;

public class TestManyRequests {

    public static void main(String[] args) {
        MockServer server = new MockServer();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                DefaultFuture df = new DefaultFuture();
                df.request(server);
                String s = null;
                try {
                    s = df.get(10000);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println(s);
            }).start();
        }
    }
}
