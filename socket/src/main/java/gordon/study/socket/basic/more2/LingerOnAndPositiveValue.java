package gordon.study.socket.basic.more2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class LingerOnAndPositiveValue {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.setReceiveBufferSize(4096);
                serverSocket.bind(new InetSocketAddress(8888));
                socket = serverSocket.accept();
                Thread.sleep(5000);
                while (true) {
                    char c = (char) socket.getInputStream().read();
                    if (c == '\0') {
                        System.out.println("read to the end at " + System.currentTimeMillis() / 1000);
                        break;
                    }
                }
                Thread.sleep(1000000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }).start();

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(8888));
            socket.setSendBufferSize(4096);
            socket.setSoLinger(true, 3); // 设置使用Linger，逗留时间为3秒。
            byte a = 'a';
            byte[] b = new byte[1024];
            Arrays.fill(b, a);
            for (int i = 0; i < 8; i++) {
                socket.getOutputStream().write(b);
            }
            socket.getOutputStream().write('\0');
        } finally {
            System.out.println("client began to close at " + System.currentTimeMillis() / 1000);
            socket.close();
            System.out.println("client closed at " + System.currentTimeMillis() / 1000);
        }
    }
}

/*
如果注释掉 setSoLinger 行，程序行为很好理解，第N秒准备关闭，第N秒关闭完成，第N+5秒服务端读结束。

setSoLinger(true, 3) 意味着 close 操作会被阻塞，要么所有发送的数据包和 close 发出的 FIN 包都被接收方底层 socket 确认，close 操作结束；
要么等待超过 linger 指定的超时时间，会发送 RST 包。

本例，第N秒准备关闭，由于部分数据还在发送缓冲区，不可能被接收方确认，因此，第N+3秒由于超时强制发送 RST 包，第N+5秒接收方读数据时抛异常。

如果改为 setSoLinger(true, 10)，第N秒准备关闭，第N+5秒接收方开始读取数据并完成读取，第N+5(或N+6)秒 close 方法在超时时间到达前提前结束。

显然，setSoLinger(true, >0) 要求 socket 是阻塞模式运行！

 */