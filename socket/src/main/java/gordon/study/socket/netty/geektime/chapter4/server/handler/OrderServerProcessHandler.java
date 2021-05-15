package gordon.study.socket.netty.geektime.chapter4.server.handler;

import gordon.study.socket.netty.geektime.chapter4.common.Operation;
import gordon.study.socket.netty.geektime.chapter4.common.OperationResult;
import gordon.study.socket.netty.geektime.chapter4.common.RequestMessage;
import gordon.study.socket.netty.geektime.chapter4.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        ctx.writeAndFlush(responseMessage);
    }


}
