package gordon.study.concurrent.geekbang.u14lockcondition.simulatedubbo;

import java.util.concurrent.TimeoutException;

public class TestTimeout {

    public static void main(String[] args) {
        DefaultFuture df = new DefaultFuture();
        df.request(new MockServer());
        String s = null;
        try {
            s = df.get(100);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }
}
