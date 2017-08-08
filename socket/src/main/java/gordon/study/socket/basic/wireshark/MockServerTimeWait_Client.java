package gordon.study.socket.basic.wireshark;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MockServerTimeWait_Client {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(8888));
            InputStream is = socket.getInputStream();
            while (is.read() != -1) {
            }
            socket.close();
        }
    }
}
