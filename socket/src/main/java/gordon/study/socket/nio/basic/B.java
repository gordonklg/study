package gordon.study.socket.nio.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class B {

    public static void main(String[] args) throws Exception {

        new Thread(new SocketClient()).start();
        new Thread(new SocketClient()).start();
        new Thread(new SocketClient()).start();

        B a = new B();
        a.test();

    }

    private void test() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8888));
            serverSocketChannel.configureBlocking(false);
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Thread(new ServerSocketHandler(socketChannel)).start();
                }
            }

            //            Selector selector = Selector.open();
            //            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class ServerSocketHandler implements Runnable {

        private SocketChannel socketChannel;

        public ServerSocketHandler(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                String newData = "New String to write to file..." + System.currentTimeMillis();
                Thread.sleep(1000); // it takes a long time to run
                ByteBuffer buf = ByteBuffer.allocate(48);
                buf.clear();
                buf.put(newData.getBytes());
                buf.flip();
                while (buf.hasRemaining()) {
                    socketChannel.write(buf);
                }
                socketChannel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class SocketClient implements Runnable {

        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(8888));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String info = null;
                while ((info = br.readLine()) != null) {
                    System.out.println(Thread.currentThread().getName() + ":" + info);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }
                socket = null;
            }
        }
    }

}
