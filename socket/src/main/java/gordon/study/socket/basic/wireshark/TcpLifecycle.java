package gordon.study.socket.basic.wireshark;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpLifecycle {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8888);
                    Socket socket = serverSocket.accept();
                    socket.getInputStream().read();
                    socket.close();
                } catch (Exception e) {
                }
            }
        }).start();

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8888));
        Thread.sleep(3000);
        socket.getOutputStream().write('a');
        socket.close();
    }
}
