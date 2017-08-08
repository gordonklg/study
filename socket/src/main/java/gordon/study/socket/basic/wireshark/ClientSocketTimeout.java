package gordon.study.socket.basic.wireshark;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocketTimeout {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8888);
                    serverSocket.accept();
                    while (true) {
                        Thread.sleep(10000);
                    }
                } catch (Exception e) {
                }
            }
        }).start();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
            socket.setSoTimeout(1000);
            socket.getInputStream().read();
        }
    }
}
