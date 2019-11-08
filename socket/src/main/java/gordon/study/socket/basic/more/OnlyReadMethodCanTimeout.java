package gordon.study.socket.basic.more;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class OnlyReadMethodCanTimeout {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            long time = 0;
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                socket.setSoTimeout(2000);
                Thread.sleep(3000); // 休眠3秒
                time = System.currentTimeMillis();
                socket.getInputStream().read(); // 并不会立即抛超时异常，而是2秒后才抛！
            } catch (Exception e) {
                time -= System.currentTimeMillis();
                System.out.println("read method blocks (seconds): " + -time / 1000.0);
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
            Thread.sleep(10000);
        }
    }
}

/*
也就是说，如果服务器端read一部分数据，然后很慢的处理，处理完再read一部分数据（客户端发送数据足够快），则并不会超时。
见 OnlyReadMethodCanTimeout2
 */