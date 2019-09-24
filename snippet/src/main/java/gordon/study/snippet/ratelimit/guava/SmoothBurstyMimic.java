package gordon.study.snippet.ratelimit.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.lang.reflect.Field;

/*
QPS=4

(1)t=0,这时候storedPermits=0，请求1个令牌，等待时间=0；
(2)t=1,这时候storedPermits=3，请求3个令牌，等待时间=0；
(3)t=2,这时候storedPermits=4，请求10个令牌，等待时间=0，超前使用了2个令牌；
(4)t=3,这时候storedPermits=0，请求1个令牌，等待时间=0.5。
 */
public class SmoothBurstyMimic {

    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(4.0);
        new Thread(() -> {
            try {
                Thread.sleep(0);
                // t=0
                inspect(rateLimiter);
                System.out.println(rateLimiter.acquire());
            } catch (InterruptedException e) {
            }
        }).start();
        new Thread(() -> {
            try {
                // t=1
                Thread.sleep(1000);
                inspect(rateLimiter);
                System.out.println(rateLimiter.acquire(3));
            } catch (InterruptedException e) {
            }
        }).start();
        new Thread(() -> {
            try {
                // t=2
                Thread.sleep(2000);
                inspect(rateLimiter);
                System.out.println(rateLimiter.acquire(10));
            } catch (InterruptedException e) {
            }
        }).start();
        new Thread(() -> {
            try {
                // t=3
                Thread.sleep(3000);
                inspect(rateLimiter);
                System.out.println(rateLimiter.acquire());
                inspect(rateLimiter);
            } catch (InterruptedException e) {
            }
        }).start();
    }

    private static void inspect(RateLimiter rateLimiter) {
        try {
            Class clazz = rateLimiter.getClass();
            Class superClazz = clazz.getSuperclass();

            Field maxPermits = null;
            if (maxPermits == null) {
                maxPermits = superClazz.getDeclaredField("maxPermits");
                maxPermits.setAccessible(true);
            }
            Field storedPermits = null;
            if (storedPermits == null) {
                storedPermits = superClazz.getDeclaredField("storedPermits");
                storedPermits.setAccessible(true);
            }
            Field stableIntervalMicros = null;
            if (stableIntervalMicros == null) {
                stableIntervalMicros = superClazz.getDeclaredField("stableIntervalMicros");
                stableIntervalMicros.setAccessible(true);
            }
            Field nextFreeTicketMicros = null;
            if (nextFreeTicketMicros == null) {
                nextFreeTicketMicros = superClazz.getDeclaredField("nextFreeTicketMicros");
                nextFreeTicketMicros.setAccessible(true);
            }

            System.out.printf("maxPermits:%s, storedPermits:%s, stableIntervalMicros:%s, nextFreeTicketMicros:%s\n",
                    maxPermits.get(rateLimiter).toString(),
                    storedPermits.get(rateLimiter).toString(),
                    stableIntervalMicros.get(rateLimiter).toString(),
                    nextFreeTicketMicros.get(rateLimiter).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
EXPECTED:
maxPermits=4.0, storedPermits=0.0, stableIntervalMicros=250000.0, nextFreeTicketMicros=1472
acquire(1), sleepSecond=0.0

maxPermits=4.0, storedPermits=3.012212, stableIntervalMicros=250000.0, nextFreeTicketMicros=1004345
acquire(3), sleepSecond=0.0

maxPermits=4.0, storedPermits=4.0, stableIntervalMicros=250000.0, nextFreeTicketMicros=2004668
acquire(10), sleepSecond=0.0

maxPermits=4.0, storedPermits=0.0, stableIntervalMicros=250000.0, nextFreeTicketMicros=3504668
acquire(1), sleepSecond=0.499591

REAL:
maxPermits:4.0, storedPermits:0.0, stableIntervalMicros:250000.0, nextFreeTicketMicros:846
0.0
maxPermits:4.0, storedPermits:0.0, stableIntervalMicros:250000.0, nextFreeTicketMicros:250846
0.0
maxPermits:4.0, storedPermits:0.21977599999999997, stableIntervalMicros:250000.0, nextFreeTicketMicros:1055790
0.0
maxPermits:4.0, storedPermits:0.0, stableIntervalMicros:250000.0, nextFreeTicketMicros:3557054
0.499929
maxPermits:4.0, storedPermits:0.0, stableIntervalMicros:250000.0, nextFreeTicketMicros:3807054
 */