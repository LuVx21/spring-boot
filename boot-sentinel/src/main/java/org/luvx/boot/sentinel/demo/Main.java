package org.luvx.boot.sentinel.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main {
    static final String helloWorld = "HelloWorld";

    @SentinelResource(value = helloWorld)
    public void helloWorld() {
        System.out.println("hello world");
    }

    private static void initFlowRules() {
        FlowRule rule = new FlowRule();
        rule.setResource(helloWorld);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setCount(5);
        FlowRuleManager.loadRules(List.of(rule));
    }

    @SneakyThrows
    public static void main(String[] args) {
        // 配置规则.
        initFlowRules();

        while (true) {
            try (Entry entry = SphU.entry(helloWorld)) {
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }
}
