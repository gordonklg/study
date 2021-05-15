package gordon.study.socket.netty.geektime.chapter4.common;

public class ResponseMessage extends Message <OperationResult>{
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationResultClazz();
    }
}
