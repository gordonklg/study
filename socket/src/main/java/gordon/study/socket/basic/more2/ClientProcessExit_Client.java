package gordon.study.socket.basic.more2;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class ClientProcessExit_Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
            socket.setSendBufferSize(4096);
            byte a = 'a';
            byte[] b = new byte[1024];
            Arrays.fill(b, a);
            for (int i = 0; i < 8; i++) {
                socket.getOutputStream().write(b);
            }
            socket.getOutputStream().write('\0');
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("client will exit at " + System.currentTimeMillis() / 1000);
    }
}
