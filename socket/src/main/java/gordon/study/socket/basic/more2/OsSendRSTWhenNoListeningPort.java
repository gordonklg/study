package gordon.study.socket.basic.more2;

import java.net.InetSocketAddress;
import java.net.Socket;

public class OsSendRSTWhenNoListeningPort {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
        }
        Thread.sleep(1000000);
    }
}

/*
没有启服务监听8888端口。
进程向8888端口发送了5次 SYN 包，OS回应了5次 [RST, ACK] 包。
也就是说建立连接默认重试5次？
 */