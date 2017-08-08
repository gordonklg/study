package gordon.study.socket.nio.basic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadLocalRandom;

public class BlockingEchoServer2 {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1; i++) {
            new Thread(new SocketClient()).start();
        }
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        serverSocketChannel.configureBlocking(false);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                new Thread(new ServerSocketHandler(socketChannel)).start();
            }
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
                ByteBuffer readBuf = ByteBuffer.allocate(48);
                ByteBuffer writeBuf = ByteBuffer.allocate(48);
                int msgLength = -1;
                byte[] msg = null;
                while (true) {
                    int size = socketChannel.read(readBuf);
                    if (size == -1) {
                        break;
                    }
                    if (msgLength == -1 && readBuf.position() >= 4) {
                        readBuf.flip();
                        msgLength = readBuf.getInt();
                        msg = new byte[msgLength];
                        if(readBuf.remaining() < msgLength ) {
                            readBuf.get(msg, 0, readBuf.remaining());
                            readBuf.clear();
                            continue;
                        } else {
                            readBuf.get(msg);
                            writeBuf.put(msg);
                            writeBuf.put("\n".getBytes());
                            writeBuf.flip();
                            while(writeBuf.hasRemaining()) {
                                socketChannel.write(writeBuf);
                            }
                            writeBuf.clear();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class SocketClient implements Runnable {

        private String[] msgArray = { "ni hao", "hello", "chi le ma?", "ni chou sha?", "hi dude" };

        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(8888));
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                for (int i = 0; i < 10000; i++) {
                    long sleepTime = ThreadLocalRandom.current().nextLong(1500) + 250;
                    send("aa", dos);
                    send("gaaf", dos);
                    send("ccccc", dos);
                    dos.flush();
                    receive(br);
                    receive(br);
                    receive(br);
                    Thread.sleep(sleepTime);
                }
                socket.close();
            } catch (Exception e) {
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

        private void send(String msg, DataOutputStream dos) throws Exception {
            byte[] bytes = msg.getBytes();
            dos.writeInt(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
//                System.out.println(bytes[i]);
                dos.write(bytes[i]);
            }
            //            dos.flush();
            System.out.println(Thread.currentThread().getName() + " :send: " + msg);
        }

        private void receive(BufferedReader br) throws Exception {
            String info = br.readLine();
            if (info != null) {
                System.out.println(Thread.currentThread().getName() + " :receive: " + info);
            }
        }
    }
}
