package org.luvx.boot.zk;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

@Slf4j
public class EmbeddedZkTest {
    @Test
    void run() throws Exception {
        List<InstanceSpec> specs = Lists.newArrayList();
        int port = 2181, electionPort = 2281, quorumPort = 2381;
        for (int i = 0; i < 3; i++) {
            InstanceSpec spec = new InstanceSpec(null, port, electionPort, quorumPort, true,
                    i, 10000, 100, null, "localhost");
            log.info("创建zk_{} -> port:{},electionPort:{},quorumPort:{}", i, port, electionPort, quorumPort);
            specs.add(spec);
            port++;
            electionPort++;
            quorumPort++;
        }

        TestingCluster cluster = new TestingCluster(specs);
        try {
            cluster.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    log.debug("停止cluster");
                    cluster.stop();
                } catch (IOException e) {
                    log.error("停止cluster异常", e);
                }
            }));
        } catch (Exception e) {
            log.error("启动cluster异常", e);
            throw e;
        }
        log.info("zk server 启动成功");
        System.in.read();
    }
}
