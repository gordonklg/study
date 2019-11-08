package gordon.study.socket.basic.more;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ClientSocketTimeoutThenServerClose {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                int i = 1;
                while (true) {
                    // System.out.println("" + socket.isClosed() + socket.isConnected() + socket.isInputShutdown() + socket.isOutputShutdown());
                    // 这些方法都是本地端的状态，无法判断远端是否已经断开连接
                    // 通过异常是最简单的方法（对方断开连接后，下面write方法会抛SocketException）
                    // 另一种常见的方法是约定发送内容的结束标记，见 CloseByContract

                    socket.getOutputStream().write('a');
                    Thread.sleep(100 * i++);
                }
            } catch (Exception e) {
                System.out.println("socket error");
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
            socket.setSoTimeout(550);
            while (true) {
                int c = socket.getInputStream().read();
                System.out.printf("%c\n", c);
            }
        }
    }
}
