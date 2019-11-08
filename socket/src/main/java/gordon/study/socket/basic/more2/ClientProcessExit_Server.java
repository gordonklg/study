package gordon.study.socket.basic.more2;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientProcessExit_Server {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new DelayHandler(socket)).start();
        }
    }

    private static class DelayHandler implements Runnable {

        private Socket socket;

        public DelayHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                while (true) {
                    char c = (char) socket.getInputStream().read();
                    if (c == '\0') {
                        System.out.println("read to the end at " + System.currentTimeMillis() / 1000);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
