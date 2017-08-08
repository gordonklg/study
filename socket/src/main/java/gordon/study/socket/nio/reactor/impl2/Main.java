package gordon.study.socket.nio.reactor.impl2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8888)).start();
        for (int i = 0; i < 10; i++) {
            new Thread(new SocketClient(i)).start();
        }
    }

    private static class SocketClient implements Runnable {

        private String[] msgArray = { "ni hao", "hello", "chi le ma?", "你瞅啥？", "hi dude" };

        private int number;

        public SocketClient(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(8888));
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                int pos = ThreadLocalRandom.current().nextInt(msgArray.length);
                sendMsg(msgArray[pos], dos);
                char result = (char) dis.read();
                System.out.printf("request result from %d: %s\n", number, result);
                dis.close();
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sendMsg(String msg, DataOutputStream dos) throws Exception {
            byte[] bytes = msg.getBytes();
            int totalLength = 4 + bytes.length;
            dos.writeInt(totalLength);
            dos.write(bytes);
        }
    }
}
