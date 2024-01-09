// package org.luvx.boot.flowable.config;
//
// import java.util.List;
//
// import com.google.common.collect.Lists;
//
// import lombok.extern.slf4j.Slf4j;
// import org.flowable.common.engine.api.delegate.FlowableFunctionDelegate;
// import org.flowable.common.engine.impl.de.odysseus.el.ExpressionFactoryImpl;
// import org.flowable.common.engine.impl.javax.el.ExpressionFactory;
// import org.flowable.spring.SpringProcessEngineConfiguration;
// import org.flowable.spring.boot.EngineConfigurationConfigurer;
// import org.luvx.boot.common.util.ApplicationContextUtils;
// import org.springframework.context.annotation.Configuration;
//
// /**
//  * flowable配置----为放置生成的流程图中中文乱码
//  */
// @Slf4j
// @Configuration
// public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
//     @Override
//     public void configure(SpringProcessEngineConfiguration config) {
//         // engineConfiguration.setActivityFontName("宋体");
//         // engineConfiguration.setLabelFontName("宋体");
//         // engineConfiguration.setAnnotationFontName("宋体");
//         initExpressFunction(config);
//     }
//
//     private void initExpressFunction(SpringProcessEngineConfiguration config) {
//         String execution = "execution";
//         VariableQueryExpressionFunction f1 = new VariableQueryExpressionFunction(execution);
//         // -----------------
//         config.initShortHandExpressionFunctions();
//         config.getShortHandExpressionFunctions().add(f1);
//         // List<FlowableFunctionDelegate> customFlowableFunctionDelegates = config.getCustomFlowableFunctionDelegates();
//         // if (customFlowableFunctionDelegates == null) {
//         //     config.setCustomFlowableFunctionDelegates(
//         //             customFlowableFunctionDelegates = Lists.newArrayList()
//         //     );
//         // }
//         // customFlowableFunctionDelegates.add(f1);
//     }
// }