package gordon.study.socket.basic.more;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SendTooMuchAndClose {

    /*
    以下是自己的理解，未证实！
    服务器端和客户端都有发送缓冲区和接收缓冲区（内核空间中）。客户端发送字节先写入自己的发送缓冲区，内核将发送缓冲区的数据通过TCP协议发送出去，
    服务器端将数据缓存到接收缓冲区，供服务器端的输入流读取（应该还涉及到从内核空间到用户空间的拷贝）。
    这就是为什么 CLoseByContract 例子中，客户端发送几个byte后立即关闭socket，服务器端连接已经变为CLOSE_WAIT状态，却依然能够读取输入流中的
    数据的原因。因为数据已经由内核（的socket模组）接收到接收缓冲区中，同时也接收到最后一个FIN包，在OS的视角看，当前连接状态就是CLOSE_WAIT，
    也符合先数据传输再四次挥手的顺序。

    本例中设置了发送方的发送缓冲区和接收方的接收缓冲区大小都是4K，而传输数据远大于4k。原来猜测发送方发太多再关闭socket会破坏顺序导致异常，
    实际上，由于两个缓冲区很快都被占满，发送方会阻塞在 write 方法上！总而言之，想多了。

    附带的知识就是TCP窗口机制，随着接收方缓冲区可用空间越来越小，TCP头上的（滑动）窗口大小也会变小。通过窗口机制保证接收方缓冲区不会溢出。
     */

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            Socket socket = null;
            try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.setReceiveBufferSize(4096);
                serverSocket.bind(new InetSocketAddress(8888));
                socket = serverSocket.accept();
                System.out.println("initial SO_RCVBUF: " + socket.getReceiveBufferSize());
                InputStream is = socket.getInputStream();
                int count = 0;
                while (true) {
                    if (++count % 1024 == 0) {
                        System.out.println("received bytes: " + count + ", SO_RCVBUF: " + socket.getReceiveBufferSize());
                        Thread.sleep(100);
                    }
                    char c = (char) is.read();
                    if (c == '\0') {
                        break;
                    }
                }
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
        socket.setSendBufferSize(4096);
        try {
            socket.connect(new InetSocketAddress(8888));
            OutputStream os = socket.getOutputStream();
            byte a = 'a';
            byte[] b = new byte[4096];
            Arrays.fill(b, a);
            for (int i = 0; i < 10; i++) {
                os.write(b);
                System.out.println("SO_SNDBUF: " + socket.getSendBufferSize());
            }
            os.write('\0');
            os.flush();
        } finally {
            socket.close();
        }
        System.out.println("Is client socket closed? " + socket.isClosed());
    }
}
