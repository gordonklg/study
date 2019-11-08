package gordon.study.socket.basic.more2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ClosedSocketReceivesDataWillSendRST {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                Thread.sleep(2000);
                socket.getOutputStream().write('a'); // 对端已在 FIN_WAIT_2 状态，一旦发送内容，对端立马发送RST重置连接。
                Thread.sleep(100);
                System.out.println(socket.isInputShutdown()); // Java层面输入流的确没有关
                // socket.getInputStream().read();
                // 上一行会导致 java.net.SocketException: Software caused connection abort: recv failed
                // 因为连接已重置，再读或写都会抛异常。
                Thread.sleep(1000000); //
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

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(8888));
            socket.getOutputStream().write('a');
        } finally {
            socket.close();
        }
        Thread.sleep(1000000);
    }
}
