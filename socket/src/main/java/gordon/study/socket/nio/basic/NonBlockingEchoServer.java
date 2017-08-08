package gordon.study.socket.nio.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import gordon.study.socket.basic.LineSeparateEchoClient;
import gordon.study.socket.basic.CustomProtocolPrintClient;

public class NonBlockingEchoServer {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            new Thread(new CustomProtocolPrintClient()).start();
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
                while (true) {
                    int size = socketChannel.read(readBuf);
                    if(size == -1) {
                        break;
                    }
                    if(msgLength == -1 && readBuf.position() >= 4) {
                        readBuf.flip();
                        msgLength = readBuf.getInt();
                        writeBuf.put(readBuf);
                        readBuf.clear();
                    }
                    if(msgLength >= 0 && readBuf.position() >= msgLength + 4) {
                        byte[] content = new byte[msgLength];
                        readBuf.get(content, 0, msgLength);
                        readBuf.compact();
                        writeBuf.put(content);
                        while (readBuf.hasRemaining()) {
                            socketChannel.write(readBuf);
                        }
                        writeBuf.clear();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
