package gordon.study.socket.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class LineSeparateEchoClient implements Runnable {

    private String[] msgArray = { "ni hao", "hello", "chi le ma?", "你瞅啥？", "hi dude" };

    @Override
    public void run() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            for (int i = 0; i < 3; i++) {
                int pos = ThreadLocalRandom.current().nextInt(msgArray.length);
                long sleepTime = ThreadLocalRandom.current().nextLong(1500) + 250;
                sendAndReceive(msgArray[pos], pw, br);
                Thread.sleep(sleepTime);
            }
            br.close();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendAndReceive(String msg, PrintWriter pw, BufferedReader br) throws Exception {
        pw.println(msg);
        pw.flush();
        System.out.println(Thread.currentThread().getName() + " :send: " + msg);
        String info = br.readLine();
        if (info != null) {
            System.out.println(Thread.currentThread().getName() + " :receive: " + info);
        }
    }
}
