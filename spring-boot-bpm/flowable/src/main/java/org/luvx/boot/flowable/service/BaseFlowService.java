package org.luvx.boot.flowable.service;

import lombok.Getter;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Getter
@Component
public class BaseFlowService {
    @Resource
    protected RepositoryService repositoryService;
    @Resource
    protected RuntimeService    runtimeService;
    @Resource
    protected HistoryService    historyService;
    @Resource
    protected IdentityService   identityService;
    @Resource
    protected TaskService       taskService;
    @Resource
    protected FormService       formService;
    @Resource
    protected ManagementService managementService;
    @Resource
    @Qualifier("processEngine")
    protected ProcessEngine     processEngine;

    /**
     * flowable获取所有节点信息
     */
    public void a() {
        String processInstanceId = "60308c05-ac56-11e9-81d0-dad8d2a12195";
        String definitionId = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getProcessDefinitionId();
        List<Process> processes = repositoryService.getBpmnModel(definitionId).getProcesses();
        System.out.println("processes size:" + processes.size());

        for (Process process : processes) {
            for (FlowElement flowElement : process.getFlowElements()) {
                if (flowElement instanceof UserTask) {
                    System.out.println("UserTask：" + flowElement.getName());
                }
                if (flowElement instanceof SubProcess) {
                }
            }
        }
    }
}