package gordon.study.rabbitmq.basic.helloworld2;

public class Test02OldQueue {

    public static void main(String[] args) throws Exception {
        NewExchangeReceiver receiver = new NewExchangeReceiver("hello", "hello", true);
        receiver.work();
        NewExchangeSender sender = new NewExchangeSender("hello", "hello", true);
        sender.work();
    }
}
