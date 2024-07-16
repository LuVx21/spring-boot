package org.luvx.boot.mars.rpc.sdk.user;

import org.apache.dubbo.config.annotation.DubboReference;
import org.luvx.boot.mars.rpc.sdk.count.CountRpcClient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * 不太好,毕竟不是个rpc, 使用时注入即可, 和调用rpc的行为不一致
 * CountRpcClient -> @DubboReference
 * UserCountRpcClient -> @Autowired
 */
@Service
@Deprecated
public class UserCountRpcClient {
    @DubboReference(check = false)
    private CountRpcClient countRpcClient;

    public Map<Long, Integer> getByType(UserCountType type, Collection<Long> userIds) {
        return countRpcClient.getByType(type, userIds);
    }
}
