package gordon.study.socket.netty.geektime.chapter4.common.order;

import gordon.study.socket.netty.geektime.chapter4.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;

}
