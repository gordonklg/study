package gordon.study.socket.netty.geektime.chapter4.client.codec;

import gordon.study.socket.netty.geektime.chapter4.common.Operation;
import gordon.study.socket.netty.geektime.chapter4.common.RequestMessage;
import gordon.study.socket.netty.geektime.chapter4.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OperationToRequestMessageEncoder extends MessageToMessageEncoder <Operation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Operation operation, List<Object> out) throws Exception {
          RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), operation);

          out.add(requestMessage);
     }
}
