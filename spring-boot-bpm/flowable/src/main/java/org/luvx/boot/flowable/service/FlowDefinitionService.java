package org.luvx.boot.flowable.service;

import org.luvx.boot.flowable.dto.FlowProcDefDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FlowDefinitionService {

    boolean exist(String processDefinitionKey);

    /**
     * 流程定义列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return 流程定义分页列表数据
     */
    List<FlowProcDefDto> list(Integer pageNum, Integer pageSize);

    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    void importFile(String name, String category, InputStream in);

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    Object readXml(String deployId, String procDefKey) throws IOException;

    /**
     * 激活或挂起流程定义
     *
     * @param state    状态
     * @param deployId 流程部署ID
     */
    void updateState(Integer state, String deployId);

    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    void delete(String deployId);

    /**
     * 读取图片文件
     *
     * @param deployId
     * @return
     */
    InputStream readImage(String deployId, String procDefKey);
}
