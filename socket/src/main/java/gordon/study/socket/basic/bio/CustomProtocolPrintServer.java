package gordon.study.socket.basic.bio;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CustomProtocolPrintServer {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            new Thread(new CustomProtocolPrintClient()).start();
        }
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new PrintServerHandler(socket)).start();
        }
    }

    private static class PrintServerHandler implements Runnable {

        private Socket socket;

        public PrintServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                while (true) {
                    int type = dis.read();
                    if (type == 0) {
                        break;
                    }
                    int length = (dis.readByte() & 0xFF) << 8 | dis.readByte() & 0xFF;
                    byte[] content = new byte[length - 3];
                    dis.readFully(content);
                    System.out.println("server received: " + new String(content));
                }
                dis.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
