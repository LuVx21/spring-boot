//package org.luvx.boot.websocket.config;
//
//import org.luvx.boot.websocket.config.factory.WebSocketDecoratorFactory;
//import org.luvx.boot.websocket.config.handler.PrincipalHandshakeHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig
//        extends AbstractWebSocketMessageBrokerConfigurer
//// implements WebSocketMessageBrokerConfigurer
//{
//
//    @Autowired
//    private WebSocketDecoratorFactory factory;
//    @Autowired
//    private PrincipalHandshakeHandler handler;
//
//    /**
//     * chat表示 你前端到时要对应url映射
//     *
//     * @param registry
//     */
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat")
//                .setAllowedOrigins("*")
//                .setHandshakeHandler(handler)
//                .withSockJS();
//    }
//
//    /**
//     * 点对点时, 服务端省略/user前缀
//     * 客户端订阅不省略
//     * 此处使用topic用于广播
//     * queue用于点对点
//     *
//     * @param registry
//     */
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        /// 客户端订阅地址的前缀, 客户端接收服务端发送消息的前缀信息
//        /// 订阅地址:广播时以此为开头, 点对点时以/user/{此处前缀}
//        registry.enableSimpleBroker("/topic", "/queue");
//        /// 服务端接收地址的前缀, 默认为空,
//        /// 客户端给服务端发消息时, 和MessageMapping中的值拼接为目标url
//        registry.setApplicationDestinationPrefixes("/app");
//        /// 点对点使用的订阅前缀, 不设置使用默认值/user/
//        /// 客户端订阅路径以此开头
//        // registry.setUserDestinationPrefix("/user");
//    }
//
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.addDecoratorFactory(factory);
//        super.configureWebSocketTransport(registration);
//    }
//}