package gordon.study.rabbitmq.workqueue;

public class Test03FairDispatch {

    public static void main(String[] args) throws Exception {
        QosAcknowledgeReceiver recv1 = new QosAcknowledgeReceiver("A", 200);
        recv1.work();
        QosAcknowledgeReceiver recv2 = new QosAcknowledgeReceiver("B", 800);
        recv2.work();
        Sender sender = new Sender("S");
        sender.work();
    }
}
