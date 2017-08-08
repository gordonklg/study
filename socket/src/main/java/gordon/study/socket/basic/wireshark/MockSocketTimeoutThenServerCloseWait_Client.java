package gordon.study.socket.basic.wireshark;

import java.net.InetSocketAddress;
import java.net.Socket;

public class MockSocketTimeoutThenServerCloseWait_Client {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(30);
            new Thread(new SocketClient()).start();
        }
    }

    private static class SocketClient implements Runnable {

        @Override
        public void run() {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(8888));
                socket.setSoTimeout(1000);
                socket.getInputStream().read();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
