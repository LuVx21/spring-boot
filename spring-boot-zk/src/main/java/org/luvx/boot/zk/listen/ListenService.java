package org.luvx.boot.zk.listen;

import javax.annotation.Resource;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ListenService {
    @Resource
    private CuratorFramework client;

    public void a() throws Exception {
        String path = "/foo/bar/1";
        // 绑定监听器 CuratorListener
        client.getCuratorListenable().addListener((a, event) ->
                log.info("事件: {}", event)
        );

        // 异步获取节点数据
        client.getData().inBackground().forPath(path);
        // 更新节点数据
        client.setData().forPath(path, "hello foobar1".getBytes());
    }

    public void b() throws Exception {
        String path = "/foo/bar/1";
        final NodeCache nodeCache = new NodeCache(client, path);
        /// 如果设置为true则在首次启动时就会缓存节点内容到Cache中。
        nodeCache.start(true);
        // nodeCache.start();
        nodeCache.getListenable().addListener(
                () -> {
                    byte[] data = nodeCache.getCurrentData().getData();
                    log.info("触发监听回调，当前节点数据为: {}", new String(data));
                }
        );
        client.setData().forPath(path, "1".getBytes());
        client.setData().forPath(path, "2".getBytes());
        client.setData().forPath(path, "3".getBytes());
        client.setData().forPath(path, "4".getBytes());
        client.setData().forPath(path, "5".getBytes());
        client.setData().forPath(path, "6".getBytes());
    }

    public void c() throws Exception {
        String path = "/foo";
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener((a, event) -> {
            log.info("-----------------------------");
            log.info("event: {}", event.getType());
            if (event.getData() != null) {
                log.info("path: {}", event.getData().getPath());
            }
            log.info("-----------------------------");
        });
        client.create().forPath(path + "/1", "data".getBytes());
        client.setData().forPath(path + "/1", "1".getBytes());
        client.setData().forPath(path + "/1", "2".getBytes());

        client.create().forPath(path + "/1/2", "data".getBytes());
        client.setData().forPath(path + "/1/2", "1".getBytes());
        client.setData().forPath(path + "/1/2", "2".getBytes());
    }

    public void d() throws Exception {
        String path = "/foo";
        TreeCache treeCache = new TreeCache(client, path);
        treeCache.getListenable().addListener((a, event) -> {
            log.info("-----------------------------");
            log.info("event: {}", event.getType());
            if (event.getData() != null) {
                log.info("path: {}", event.getData().getPath());
            }
            log.info("-----------------------------");
        });
        treeCache.start();
        client.create().forPath(path + "/2", "data".getBytes());
        client.setData().forPath(path + "/2", "1".getBytes());
        client.setData().forPath(path + "/2", "2".getBytes());

        client.create().forPath(path + "/2/2", "data".getBytes());
        client.setData().forPath(path + "/2/2", "1".getBytes());
        client.setData().forPath(path + "/2/2", "2".getBytes());
    }
}
