package gordon.study.socket.basic.more2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class LingerOnAndValueZero {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                while (true) {
                    char c = (char) socket.getInputStream().read();
                    System.out.println(c);
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
            System.out.println(socket.getSoLinger()); // 默认-1，表示未使用Linger
            socket.setSoLinger(true, 0); // 设置使用Linger，逗留时间为0。相当于socket关闭时立即发送 RST 包。
            for (int i = 0; i < 100; i++) {
                socket.getOutputStream().write('a');
            }
            Thread.sleep(1000);
        }
    }
}
