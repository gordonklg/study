package gordon.study.socket.netty.geektime.chapter4.common.keepalive;

import gordon.study.socket.netty.geektime.chapter4.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
