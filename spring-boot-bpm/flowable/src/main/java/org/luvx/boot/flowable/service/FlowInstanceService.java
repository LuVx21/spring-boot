package org.luvx.boot.flowable.service;

import org.flowable.engine.history.HistoricProcessInstance;
import org.luvx.boot.flowable.vo.FlowTaskVo;

import java.io.InputStream;
import java.util.Map;

public interface FlowInstanceService {

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程定义Id
     * @param variables 流程变量
     */
    Object startProcessInstanceById(String procDefId, String procDefKey, Map<String, Object> variables);

    Object queryListByInstanceId(String instanceId, String userId);

    /**
     * 结束流程实例
     */
    void stopProcessInstance(FlowTaskVo vo);

    /**
     * 激活或挂起流程实例
     *
     * @param state      状态
     * @param instanceId 流程实例ID
     */
    void updateState(Integer state, String instanceId);

    /**
     * 删除流程实例ID
     *
     * @param instanceId   流程实例ID
     * @param deleteReason 删除原因
     */
    void delete(String instanceId, String deleteReason);

    /**
     * 根据实例ID查询历史实例数据
     */
    HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId);

    InputStream genProcessDiagram(String processId) throws Exception;
}
