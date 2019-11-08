package gordon.study.socket.basic.more;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTimeout {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                socket.setSoTimeout(1000);
                socket.getInputStream().read();
                // 如果不调用上一行read，换成 sleep，则并不会抛出 SocketException ！
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    // 如果不调用 close 方法，连接状态依然是 ESTABLISHED ！
                } catch (IOException e) {
                }
            }
        }).start();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
            while (true) {
                Thread.sleep(10000);
            }
        }
    }
}
