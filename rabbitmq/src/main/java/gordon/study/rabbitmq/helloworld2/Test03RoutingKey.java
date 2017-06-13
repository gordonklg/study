package gordon.study.rabbitmq.helloworld2;

public class Test03RoutingKey {

    public static void main(String[] args) throws Exception {
        NewExchangeReceiver receiver = new NewExchangeReceiver("hello", "abc", true);
        receiver.work();
        NewExchangeSender sender = new NewExchangeSender("hello", "abc", true);
        sender.work();
    }
}
