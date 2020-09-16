package gordon.study.socket.basic.bio;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class CustomProtocolPrintClient implements Runnable {

    private String[] msgArray = { "ni hao", "hello", "chi le ma?", "你瞅啥？", "hi dude" };

    @Override
    public void run() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(8888));
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            for (int i = 0; i < 3; i++) {
                int pos = ThreadLocalRandom.current().nextInt(msgArray.length);
                long sleepTime = ThreadLocalRandom.current().nextLong(1500) + 250;
                sendMsg(msgArray[pos], dos);
                Thread.sleep(sleepTime);
            }
            dos.writeByte(0);
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String msg, DataOutputStream dos) throws Exception {
        byte[] bytes = msg.getBytes();
        int totalLength = 3 + bytes.length;
        dos.writeByte(1);
        dos.write((byte) (totalLength >> 8 & 0xFF));
        dos.write((byte) (totalLength & 0xFF));
        dos.write(bytes);
    }
}
