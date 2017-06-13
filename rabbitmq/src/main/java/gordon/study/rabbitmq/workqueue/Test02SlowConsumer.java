package gordon.study.rabbitmq.workqueue;

public class Test02SlowConsumer {

    public static void main(String[] args) throws Exception {
        Receiver recv1 = new Receiver("A", 200);
        recv1.work();
        Receiver recv2 = new Receiver("B", 800);
        recv2.work();
        Sender sender = new Sender("S");
        sender.work();
    }
}
