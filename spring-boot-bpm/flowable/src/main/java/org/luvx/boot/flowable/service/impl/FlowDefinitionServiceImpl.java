package org.luvx.boot.flowable.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.luvx.boot.flowable.dto.FlowProcDefDto;
import org.luvx.boot.flowable.service.BaseFlowService;
import org.luvx.boot.flowable.service.FlowDefinitionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FlowDefinitionServiceImpl extends BaseFlowService implements FlowDefinitionService {
    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    public String deployment(String deploymentName) {
        return repositoryService.createDeployment()
                .addClasspathResource("process/" + deploymentName + ".bpmn20.xml")
                .deploy()
                .getId();
    }

    @Override
    public boolean exist(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey);
        return processDefinitionQuery.count() > 0;
    }

    /**
     * 流程定义列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return 流程定义分页列表数据
     */
    @Override
    public List<FlowProcDefDto> list(Integer pageNum, Integer pageSize) {
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                // .latestVersion()
                .orderByProcessDefinitionKey()
                .asc()
                .listPage(pageNum - 1, pageSize);

        List<FlowProcDefDto> dataList = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery()
                    // .orderByDeploymentTime()
                    // .desc()
                    .deploymentId(deploymentId)
                    .singleResult();
            FlowProcDefDto reProcDef = new FlowProcDefDto();
            BeanUtils.copyProperties(processDefinition, reProcDef);
            // 流程定义时间
            reProcDef.setDeploymentTime(deployment.getDeploymentTime());
            dataList.add(reProcDef);
        }
        return dataList;
    }

    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    @Override
    public void importFile(String name, String category, InputStream in) {
        Deployment deploy = repositoryService.createDeployment()
                // .key()
                .name(name)
                .addInputStream(name + BPMN_FILE_SUFFIX, in)
                .category(category)
                .deploy();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), category);
    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    @Override
    public Object readXml(String deployId, String procDefKey) throws IOException {
        return repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .processDefinitionKey(procDefKey)
                .list()
                .stream()
                .map(def -> repositoryService.getResourceAsStream(def.getDeploymentId(), def.getResourceName()))
                .map(is -> {
                    try {
                        return IOUtils.toString(is, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        return "";
                    }
                })
                .collect(Collectors.joining("\n"));
    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    @Override
    public InputStream readImage(String deployId, String procDefKey) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .processDefinitionKey(procDefKey)
                .singleResult();
        //获得图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        //输出为图片
        return diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                Collections.emptyList(),
                Collections.emptyList(),
                "宋体",
                "宋体",
                "宋体",
                null,
                1.0,
                false);
    }

    /**
     * 激活或挂起流程定义
     *
     * @param state    状态
     * @param deployId 流程部署ID
     */
    @Override
    public void updateState(Integer state, String deployId) {
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        // 激活
        if (state == 1) {
            repositoryService.activateProcessDefinitionById(procDef.getId(), true, null);
            // 挂起
        } else if (state == 2) {
            repositoryService.suspendProcessDefinitionById(procDef.getId(), true, null);
        }
    }

    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    @Override
    public void delete(String deployId) {
        // true 允许级联删除, 不设置会导致数据库外键关联异常
        repositoryService.deleteDeployment(deployId, true);
    }
}
