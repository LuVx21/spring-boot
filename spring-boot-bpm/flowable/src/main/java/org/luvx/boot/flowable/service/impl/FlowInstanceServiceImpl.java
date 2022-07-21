package org.luvx.boot.flowable.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.luvx.boot.flowable.service.BaseFlowService;
import org.luvx.boot.flowable.service.FlowInstanceService;
import org.luvx.boot.flowable.vo.FlowTaskVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FlowInstanceServiceImpl extends BaseFlowService implements FlowInstanceService {

    @Override
    public Object queryListByInstanceId(String instanceId, String userId) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (StringUtils.isNotEmpty(instanceId)) {
            taskQuery.processInstanceId(instanceId);
        }
        if (StringUtils.isNotEmpty(userId)) {
            taskQuery.taskAssignee(userId);
        }
        List<Task> tasks = taskQuery
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list();
        return tasks.stream()
                .map(x -> (TaskEntityImpl) x)
                .map(t -> {
                    Map<String, Object> map = (Map<String, Object>) t.getPersistentState();
                    map.put("id", t.getId());
                    return map;
                })
                .toList();
    }

    /**
     * 结束流程实例
     *
     * @param vo
     */
    @Override
    public void stopProcessInstance(FlowTaskVo vo) {
        String taskId = vo.getTaskId();
    }

    /**
     * 激活或挂起流程实例
     *
     * @param state      状态
     * @param instanceId 流程实例ID
     */
    @Override
    public void updateState(Integer state, String instanceId) {

        // 激活
        if (state == 1) {
            runtimeService.activateProcessInstanceById(instanceId);
        }
        // 挂起
        if (state == 2) {
            runtimeService.suspendProcessInstanceById(instanceId);
        }
    }

    /**
     * 删除流程实例ID
     *
     * @param instanceId   流程实例ID
     * @param deleteReason 删除原因
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String instanceId, String deleteReason) {

        // 查询历史数据
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstanceById(instanceId);
        if (historicProcessInstance.getEndTime() != null) {
            historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
            return;
        }
        // 删除流程实例
        runtimeService.deleteProcessInstance(instanceId, deleteReason);
        // 删除历史流程实例
        historyService.deleteHistoricProcessInstance(instanceId);
    }

    /**
     * 根据实例ID查询历史实例数据
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            throw new FlowableObjectNotFoundException("流程实例不存在: " + processInstanceId);
        }
        return historicProcessInstance;
    }

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程定义Id
     * @param variables 流程变量
     * @return
     */
    @Override
    public Object startProcessInstanceById(String procDefId, String procDefKey, Map<String, Object> variables) {
        try {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            if (StringUtils.isNotBlank(procDefId)) {
                processDefinitionQuery.processDefinitionId(procDefId);
            }
            if (StringUtils.isNotBlank(procDefKey)) {
                processDefinitionQuery.processDefinitionKey(procDefKey);
            }
            ProcessDefinition def = processDefinitionQuery
                    .latestVersion()
                    .singleResult();
            if (Objects.nonNull(def) && def.isSuspended()) {
                return "流程已被挂起,请先激活流程";
            }
            variables.put("initiator", "userId");
            variables.put("_FLOWABLE_SKIP_EXPRESSION_ENABLED", true);
            variables.put("INITIATOR", "");
            // variables.put("skip", true);
            // variables.put(ProcessConstants.FLOWABLE_SKIP_EXPRESSION_ENABLED, true);
            // identityService.setAuthenticatedUserId(sysUser.getUserId().toString());
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(def.getId(), variables);
            // 给第一步申请人节点设置任务执行人和意见 todo:第一个节点不设置为申请人节点有点问题？
            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstance.getProcessInstanceId())
                    .singleResult();
            if (Objects.nonNull(task)) {
                // taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), FlowComment.NORMAL.getType(), sysUser.getNickName() + "发起流程申请");
                // taskService.setAssignee(task.getId(), sysUser.getUserId().toString());
                taskService.complete(task.getId(), variables);
            }
            return Map.of(
                    "msg", "提交成功",
                    "流程Id", "Expense",
                    "实例Id", processInstance.getId()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "流程启动错误";
        }
    }

    /**
     * 查询流程实例
     */
    public List<ProcessInstance> getProcessInstanceList(String processDefinitionId) {
        return runtimeService.createProcessInstanceQuery()
                .processDefinitionId(processDefinitionId)
                .list();
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @Override
    public InputStream genProcessDiagram(String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId)
                .singleResult();
        //流程走完的不显示图
        if (pi == null) {
            return null;
        }
        Task task = taskService.createTaskQuery()
                .processInstanceId(pi.getId())
                .singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();
        //得到正在执行的Activity的Id
        List<String> activityIds = executions.stream()
                .map(exe -> runtimeService.getActiveActivityIds(exe.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        List<String> flows = new ArrayList<>();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        return diagramGenerator.generateDiagram(bpmnModel, "png",
                activityIds, flows,
                engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(),
                engconf.getClassLoader(), 1.0, false);
    }
}