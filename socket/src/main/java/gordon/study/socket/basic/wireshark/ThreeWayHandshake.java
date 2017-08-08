package gordon.study.socket.basic.wireshark;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreeWayHandshake {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8888);
                    Socket socket = serverSocket.accept();
                    while(true) {
                        System.out.println(socket.getInputStream().read());
                    }
                } catch (Exception e) {
                }
            }
        }).start();
       
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8888));
    }
}
