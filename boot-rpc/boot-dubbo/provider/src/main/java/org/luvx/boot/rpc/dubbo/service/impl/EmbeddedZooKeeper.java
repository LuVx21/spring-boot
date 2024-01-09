package org.luvx.boot.rpc.dubbo.service.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.ErrorHandler;
import org.springframework.util.SocketUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.UUID;

@Slf4j
public class EmbeddedZooKeeper implements SmartLifecycle {
    @Getter
    private final int clientPort;
    @Setter
    @Getter
    private int phase = 0;
    private volatile Thread zkServerThread;
    private volatile ZooKeeperServerMain zkServer;
    @Setter
    private ErrorHandler errorHandler;
    private boolean daemon = true;

    public EmbeddedZooKeeper() {
        clientPort = SocketUtils.findAvailableTcpPort();
    }

    public EmbeddedZooKeeper(int clientPort, boolean daemon) {
        this.clientPort = clientPort;
        this.daemon = daemon;
    }

    @Override
    public boolean isRunning() {
        return (zkServerThread != null);
    }

    /**
     * Start the ZooKeeper server in a background thread.
     * <p>
     * Register an error handler via {@link #setErrorHandler} in order to handle
     * any exceptions thrown during startup or execution.
     */
    @Override
    public synchronized void start() {
        if (zkServerThread == null) {
            zkServerThread = new Thread(new ServerRunnable(), "ZooKeeper Server Starter");
            zkServerThread.setDaemon(daemon);
            zkServerThread.start();
        }
    }

    /**
     * Shutdown the ZooKeeper server.
     */
    @Override
    public synchronized void stop() {
        if (zkServerThread != null) {
            // The shutdown method is protected...thus this hack to invoke it.
            // This will log an exception on shutdown; see
            // https://issues.apache.org/jira/browse/ZOOKEEPER-1873 for details.
            try {
                Method shutdown = ZooKeeperServerMain.class.getDeclaredMethod("shutdown");
                shutdown.setAccessible(true);
                shutdown.invoke(zkServer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // It is expected that the thread will exit after
            // the server is shutdown; this will block until
            // the shutdown is complete.
            try {
                zkServerThread.join(5000);
                zkServerThread = null;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Interrupted while waiting for embedded ZooKeeper to exit");
                // abandoning zk thread
                zkServerThread = null;
            }
        }
    }

    /**
     * Runnable implementation that starts the ZooKeeper server.
     */
    private class ServerRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Properties properties = new Properties();
                File file = new File(System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID());
                file.deleteOnExit();
                properties.setProperty("dataDir", file.getAbsolutePath());
                properties.setProperty("clientPort", String.valueOf(clientPort));

                QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();
                quorumPeerConfig.parseProperties(properties);

                zkServer = new ZooKeeperServerMain();
                ServerConfig configuration = new ServerConfig();
                configuration.readFrom(quorumPeerConfig);

                zkServer.runFromConfig(configuration);
            } catch (Exception e) {
                if (errorHandler != null) {
                    errorHandler.handleError(e);
                } else {
                    log.error("启动内嵌ZooKeeper失败", e);
                }
            }
        }
    }
}
