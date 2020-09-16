package gordon.study.socket.basic.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LineSeparateEchoServer {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            new Thread(new LineSeparateEchoClient()).start();
        }
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new EchoServerHandler(socket)).start();
        }
    }

    private static class EchoServerHandler implements Runnable {

        private Socket socket;

        public EchoServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String info = null;
                while ((info = br.readLine()) != null) {
                    pw.println(info);
                    pw.flush();
                }
                br.close();
                pw.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
