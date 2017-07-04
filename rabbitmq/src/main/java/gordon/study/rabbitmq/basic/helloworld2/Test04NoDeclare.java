package gordon.study.rabbitmq.basic.helloworld2;

public class Test04NoDeclare {

    public static void main(String[] args) throws Exception {
        NewExchangeReceiver receiver = new NewExchangeReceiver("hello", "abc", false);
        receiver.work();
        NewExchangeSender sender = new NewExchangeSender("hello", "abc", false);
        sender.work();
    }
}
