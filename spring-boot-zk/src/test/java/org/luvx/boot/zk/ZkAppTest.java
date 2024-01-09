package org.luvx.boot.zk;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Uninterruptibles;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class ZkAppTest {
    TestingCluster cluster;

    @BeforeEach
    void before() {
        cluster = new TestingCluster(3);
    }

    @Test
    void TestCluster() throws Exception {
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        TestingZooKeeperServer leader = null;
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            log.info("zk server:{}-{}-{}",
                    zs.getInstanceSpec().getServerId(),
                    zs.getInstanceSpec().getConnectString(),
                    zs.getQuorumPeer().getServerState()
                    // zs.getInstanceSpec().getDataDirectory().getAbsolutePath()
            );

            if (zs.getQuorumPeer().getServerState().equals("leading")) {
                leader = zs;
            }
        }


        leader.kill();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        log.info("kill leader节点");

        for (TestingZooKeeperServer zs : cluster.getServers()) {
            log.info("选举后zk server:{}-{}-{}",
                    zs.getInstanceSpec().getServerId(),
                    zs.getInstanceSpec().getConnectString(),
                    zs.getQuorumPeer().getServerState()
                    // zs.getInstanceSpec().getDataDirectory().getAbsolutePath()
            );
        }
    }

    @AfterAll
    public void teardown() throws Exception {
        cluster.stop();
    }
}