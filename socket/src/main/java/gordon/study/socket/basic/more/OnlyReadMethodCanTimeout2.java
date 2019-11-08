package gordon.study.socket.basic.more;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class OnlyReadMethodCanTimeout2 {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                socket.setSoTimeout(1000);
                while (true) {
                    char c = (char) socket.getInputStream().read();
                    System.out.println(c);
                    Thread.sleep(1500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }).start();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
            for (int i = 0; i < 100; i++) {
                socket.getOutputStream().write('a');
            }
            Thread.sleep(10000);
        }
    }
}
