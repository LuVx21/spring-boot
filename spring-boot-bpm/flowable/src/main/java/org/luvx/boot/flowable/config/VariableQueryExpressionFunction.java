// package org.luvx.boot.flowable.config;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import com.google.common.collect.Lists;
//
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.commons.lang3.math.NumberUtils;
// import org.flowable.common.engine.impl.el.function.AbstractFlowableVariableExpressionFunction;
// import org.flowable.variable.api.delegate.VariableScope;
//
// @Slf4j
// public class VariableQueryExpressionFunction extends AbstractFlowableVariableExpressionFunction {
//     public VariableQueryExpressionFunction(String variableScopeName) {
//         super(variableScopeName, "renxie");
//     }
//
//     @Override
//     protected boolean isMultiParameterFunction() {
//         return true;
//     }
//
//     public static boolean renxie(VariableScope variableScope, String variableNames, String containKey) {
//         log.info("入参:{} {}", variableNames, containKey);
//         List<String> result = Lists.newArrayList();
//         String[] variables = StringUtils.split(variableNames, ",");
//
//         for (String var : variables) {
//             Object variableValue = getVariableValue(variableScope, var);
//             result.add(variableValue.toString());
//         }
//         log.info("变量名:{}", variableNames);
//         log.info("变量值:{}", result);
//
//         int i = NumberUtils.toInt("400");
//         return i <= 500;
//     }
//
//     // public static boolean test(String strIds, String id) {
//     //     log.info("customFunc.containsFunc: strIds:{}, id:{}", strIds, id);
//     //     if (StringUtils.isEmpty(strIds) || StringUtils.isEmpty(id)) {
//     //         return false;
//     //     }
//     //     List<String> ids = Arrays.asList(strIds.split(","));
//     //     return ids.contains(id);
//     // }
//
//     // public static String query(VariableScope variableScope, String variableNames) {
//     //
//     //     List<String> result = new ArrayList<>();
//     //     String[] variables = StringUtils.split(variableNames, MyFlowableConstants.SEPARATOR);
//     //
//     //     for (String var : variables) {
//     //         Object variableValue = getVariableValue(variableScope, var);
//     //         result.add(variableValue.toString());
//     //     }
//     //     QueryService queryService = SpringContextUtils.getBean(QueryService.class);
//     //     return queryService.querySomeThing(result);
//     // }
// }
