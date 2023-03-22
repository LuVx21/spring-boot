//package org.luvx.boot.zk
//
//import org.apache.curator.framework.CuratorFramework
//import org.luvx.boot.zk.listen.ListenService
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import javax.annotation.Resource
//
//private val log = mu.KotlinLogging.logger {}
//
///**
// *
// * @author Ren, Xie
// */
//@RequestMapping("/use")
//@RestController
//class UseController {
//    @Resource
//    lateinit var client: CuratorFramework
//
//    @Resource
//    lateinit var service: ListenService
//
//    @GetMapping("/create")
//    fun aa() {
//        val path = "/foo/bar/2"
//        val stat = client.checkExists().forPath(path)
//        if (stat != null) {
//            log.info { "存在:$path" }
//            client.delete().deletingChildrenIfNeeded().forPath(path)
//        }
//        // client.create().creatingParentsIfNeeded().forPath(path)
//        val r = client.create().creatingParentContainersIfNeeded().forPath(path)
//        log.info { "创建结果:$r" }
//
//        val childrenList = client.children.forPath("/foo")
//        log.info { "子节点: $childrenList" }
//
//        val forPath = client.data.forPath(path)
//        log.info("节点数据: {}", String(forPath))
//
//        client.setData().forPath(path, "hello foobar".toByteArray())
//    }
//
//    @GetMapping("/bb")
//    fun bb() {
//        service.a()
//        service.b()
//        service.c()
//        service.d()
//    }
//}