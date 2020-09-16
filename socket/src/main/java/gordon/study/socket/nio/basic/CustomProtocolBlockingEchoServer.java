package gordon.study.socket.nio.basic;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import gordon.study.socket.basic.bio.LineSeparateEchoClient;

public class CustomProtocolBlockingEchoServer {

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
                ByteBuffer readBuf = ByteBuffer.allocate(48);
                ByteBuffer writeBuf = ByteBuffer.allocate(48);
                int type = -1;
                int length = -1;
                byte[] lengthBytes = new byte[2];
                byte[] content;
                while (true) {
                    int size = socketChannel.read(readBuf);
                    if (size == -1) {
                        break;
                    }
                    if (size == 0) {
                        continue;
                    }
                    readBuf.flip();
                    if (type == -1) {
                        type = readBuf.get();
                        size--;
                        if (type == 0) {
                            break;
                        }
                    }
                    if (length == -1) {
                        if (size > 0) {
                            lengthBytes[0] = readBuf.get();
                            length = -2;
                            size--;
                        } else {
                            continue;
                        }
                    }
                    if (length == -2) {
                        if (readBuf.hasRemaining()) {
                            lengthBytes[1] = readBuf.get();
                            length = (lengthBytes[0] & 0xFF) << 8 | lengthBytes[1] & 0xFF;
                            content = new byte[length - 3];
                            size--;
                        } else {
                            continue;
                        }
                    }

//                    if(content != null) {
//                        while (readBuf.hasRemaining()) {
//                            readBuf.get()
//                        }
//                    }
//                    if (msgLength == -1 && readBuf.position() >= 4) {
//                        readBuf.flip();
//                        msgLength = readBuf.getInt();
//                        writeBuf.put(readBuf);
//                        readBuf.clear();
//                    }
//                    if (msgLength >= 0 && readBuf.position() >= msgLength + 4) {
//                        byte[] content = new byte[msgLength];
//                        readBuf.get(content, 0, msgLength);
//                        readBuf.compact();
//                        writeBuf.put(content);
//                        while (readBuf.hasRemaining()) {
//                            socketChannel.write(readBuf);
//                        }
//                        writeBuf.clear();
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
