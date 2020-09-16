package gordon.study.socket.nio.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import gordon.study.socket.basic.bio.LineSeparateEchoClient;

public class LineSeparateBlockingEchoServer {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            new Thread(new LineSeparateEchoClient()).start();
        }
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            new Thread(new EchoServerHandler(socketChannel)).start();
        }
    }

    private static class EchoServerHandler implements Runnable {

        private SocketChannel socketChannel;

        public EchoServerHandler(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                ByteBuffer buf = ByteBuffer.allocate(48);
                BufferedReader br = new BufferedReader(new InputStreamReader(socketChannel.socket().getInputStream()));
                while (true) {
                    String info = br.readLine();
                    if (info != null) {
                        buf.clear();
                        buf.put(info.getBytes());
                        buf.put("\n".getBytes());
                        buf.flip();
                        while (buf.hasRemaining()) {
                            socketChannel.write(buf);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
