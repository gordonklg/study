package gordon.study.socket.nio.reactor.impl2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public final class Handler implements Runnable {

    private final SocketChannel socket;

    private final SelectionKey sk;

    private ByteBuffer input = ByteBuffer.allocate(1024);

    private ByteBuffer output = ByteBuffer.allocate(1024);

    private static final int READING = 0, SENDING = 1, PROCESSING = 2;

    private int state = READING;

    public Handler(Selector sel, SocketChannel c) throws IOException {
        socket = c;
        c.configureBlocking(false);
        sk = socket.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean inputIsComplete() {
        if (input.position() >= 4) {
            int length = input.getInt();
            return input.position() >= length;
        }
        return false;
    }

    private boolean outputIsComplete() {
        return output.remaining() == 0;
    }

    private void process() {
        input.flip();
        byte[] bytes = new byte[input.getInt() - 4];
        input.get(bytes);
        String msg = new String(bytes);
        System.out.println("Processing ... " + msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Processed ... " + msg);
        output.put((byte) 'Y');
        output.flip();
    }

    private void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()) {
            state = PROCESSING;
            new Thread(new Processer()).start();
        }
    }

    private void send() throws IOException {
        socket.write(output);
        if (outputIsComplete()) {
            sk.cancel();
        }
    }

    synchronized void processAndHandOff() {
        process();
        state = SENDING;
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.selector().wakeup();
    }

    private class Processer implements Runnable {
        public void run() {
            processAndHandOff();
        }
    }
}
