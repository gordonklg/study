package gordon.study.socket.basic.more;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseByContract {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                while (true) {
                    Thread.sleep(1000);
                    char c = (char) is.read(); // 因为sleep了一秒，这时候已经在CLOSE_WAIT状态，但是依然能获取数据。是因为底层有缓冲区吗？
                    System.out.println(c + " : " + socket.isClosed()); // 注意，CLOSE_WAIT是OS层面连接状态，Java中这个socket可没有关闭！
                    if (c == '\0') {
                        break;
                    }
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
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }).start();

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(8888));
            OutputStream os = socket.getOutputStream();
            os.write('a');
            os.write('b');
            os.write('\0');
            os.flush();
        } finally {
            socket.close();
        }
        System.out.println("Is client socket closed? " + socket.isClosed());
    }
}
