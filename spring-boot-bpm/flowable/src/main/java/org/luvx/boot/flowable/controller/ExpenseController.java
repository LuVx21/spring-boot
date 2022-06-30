package org.luvx.boot.flowable.controller;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "expense")
public class ExpenseController {
    @Autowired
    private RuntimeService    runtimeService;
    @Autowired
    private TaskService       taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine     processEngine;

    /**
     * 添加报销
     *
     * @param userId 用户Id
     * @param money  报销金额
     */
    @RequestMapping(value = "add")
    public Object addExpense(String userId, Integer money) {
        //启动流程
        Map<String, Object> map = Map.of(
                "taskUser", userId,
                "money", money
        );
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return Map.of(
                "msg", "提交成功",
                "流程Id", "Expense",
                "实例Id", processInstance.getId()
        );
    }

    /**
     * 获取审批管理列表
     */
    @SneakyThrows
    @RequestMapping(value = "/list")
    public Object list(String userId) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (StringUtils.isNotEmpty(userId)) {
            taskQuery.taskAssignee(userId);
        }
        List<Task> tasks = taskQuery
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
     * 批准
     *
     * @param taskId 任务ID
     */
    @RequestMapping(value = "apply")
    public Object apply(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        Map<String, Object> map = Map.of("outcome", "通过");
        taskService.complete(taskId, map);
        return Map.of(
                "taskId", taskId,
                "result", map
        );
    }

    /**
     * 拒绝
     */
    @RequestMapping(value = "reject")
    public Object reject(String taskId) {
        Map<String, Object> map = Map.of("outcome", "驳回");
        taskService.complete(taskId, map);
        return Map.of(
                "taskId", taskId,
                "result", map
        );
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId)
                .singleResult();
        //流程走完的不显示图
        if (pi == null) {
            return;
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
        int legth = 0;
        byte[] buf = new byte[1024];
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png",
                activityIds, flows,
                engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(),
                engconf.getClassLoader(), 1.0, false);
             OutputStream out = httpServletResponse.getOutputStream()
        ) {
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        }
    }
}