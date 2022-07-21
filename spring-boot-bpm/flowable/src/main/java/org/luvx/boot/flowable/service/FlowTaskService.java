package org.luvx.boot.flowable.service;

import org.flowable.task.api.Task;
import org.luvx.boot.flowable.vo.FlowTaskVo;

import java.io.InputStream;

public interface FlowTaskService {

    /**
     * 审批任务
     *
     * @param task 请求实体参数
     */
    void complete(FlowTaskVo task);

    /**
     * 驳回任务
     *
     * @param flowTaskVo
     */
    void taskReject(FlowTaskVo flowTaskVo);

    /**
     * 退回任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void taskReturn(FlowTaskVo flowTaskVo);

    /**
     * 获取所有可回退的节点
     *
     * @param flowTaskVo
     * @return
     */
    Object findReturnTaskList(FlowTaskVo flowTaskVo);

    /**
     * 删除任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void deleteTask(FlowTaskVo flowTaskVo);

    /**
     * 认领/签收任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void claim(FlowTaskVo flowTaskVo);

    /**
     * 取消认领/签收任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void unClaim(FlowTaskVo flowTaskVo);

    /**
     * 委派任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void delegateTask(FlowTaskVo flowTaskVo);

    /**
     * 转办任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void assignTask(FlowTaskVo flowTaskVo);

    /**
     * 我发起的流程
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Object myProcess(Integer pageNum, Integer pageSize);

    /**
     * 取消申请
     *
     * @param flowTaskVo
     * @return
     */
    void stopProcess(FlowTaskVo flowTaskVo);

    /**
     * 撤回流程
     *
     * @param flowTaskVo
     * @return
     */
    Object revokeProcess(FlowTaskVo flowTaskVo);

    /**
     * 代办任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return
     */
    Object todoList(Integer pageNum, Integer pageSize);

    /**
     * 已办任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return
     */
    Object finishedList(Integer pageNum, Integer pageSize);

    /**
     * 流程历史流转记录
     *
     * @param procInsId 流程实例Id
     * @return
     */
    Object flowRecord(String procInsId, String deployId);

    /**
     * 根据任务ID查询挂载的表单信息
     *
     * @param taskId 任务Id
     * @return
     */
    Task getTaskForm(String taskId);

    /**
     * 获取流程过程图
     *
     * @param processId
     * @return
     */
    InputStream diagram(String processId);

    /**
     * 获取流程执行过程
     *
     * @param procInsId
     * @return
     */
    Object getFlowViewer(String procInsId);

    /**
     * 获取流程变量
     *
     * @param taskId
     * @return
     */
    Object processVariables(String taskId);

    /**
     * 获取下一节点
     *
     * @param flowTaskVo 任务
     * @return
     */
    // Object getNextFlowNode(FlowTaskVo flowTaskVo);
}
