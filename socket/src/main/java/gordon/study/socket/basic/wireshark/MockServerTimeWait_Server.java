package gordon.study.socket.basic.wireshark;

import java.net.ServerSocket;
import java.net.Socket;

public class MockServerTimeWait_Server {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            Socket socket = serverSocket.accept();
            socket.close();
        }
    }
}
