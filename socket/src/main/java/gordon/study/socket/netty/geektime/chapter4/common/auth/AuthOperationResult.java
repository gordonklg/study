package gordon.study.socket.netty.geektime.chapter4.common.auth;

import gordon.study.socket.netty.geektime.chapter4.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
