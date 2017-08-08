package gordon.study.socket.basic.wireshark;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class MockSocketTimeoutThenServerCloseWait_Server {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        Set<Socket> set = new HashSet<>();
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            Socket socket = serverSocket.accept();
            set.add(socket); // anti gc.
        }
    }
}
