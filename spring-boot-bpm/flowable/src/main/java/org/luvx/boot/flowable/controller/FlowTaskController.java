package org.luvx.boot.flowable.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.luvx.boot.flowable.service.FlowTaskService;
import org.luvx.boot.flowable.util.SecurityUtils;
import org.luvx.boot.flowable.vo.FlowTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/flowable/task")
public class FlowTaskController {

    @Autowired
    private FlowTaskService flowTaskService;

    @GetMapping(value = "/myProcess")
    public Object myProcess(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize) {
        return flowTaskService.myProcess(pageNum, pageSize);
    }

    @PostMapping(value = "/stopProcess")
    public void stopProcess(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.stopProcess(flowTaskVo);
    }

    @PostMapping(value = "/revokeProcess")
    public Object revokeProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.revokeProcess(flowTaskVo);
    }

    @GetMapping(value = "/todoList")
    public Object todoList(@RequestParam(defaultValue = "0") Integer pageNum,
                           @RequestParam(defaultValue = "20") Integer pageSize) {
        return flowTaskService.todoList(pageNum, pageSize);
    }

    @GetMapping(value = "/finishedList")
    public Object finishedList(@RequestParam(defaultValue = "0") Integer pageNum,
                               @RequestParam(defaultValue = "20") Integer pageSize) {
        return flowTaskService.finishedList(pageNum, pageSize);
    }

    @GetMapping(value = "/flowRecord")
    public Object flowRecord(String procInsId, String deployId) {
        return flowTaskService.flowRecord(procInsId, deployId);
    }

    @GetMapping(value = "/processVariables/{taskId}")
    public Object processVariables(@PathVariable(value = "taskId") String taskId) {
        return flowTaskService.processVariables(taskId);
    }

    @PostMapping(value = "/complete")
    public void complete(@RequestBody FlowTaskVo flowTaskVo) {
        SecurityUtils.setUserId(flowTaskVo.getAssignee());
        flowTaskService.complete(flowTaskVo);
    }

    @PostMapping(value = "/reject")
    public Object taskReject(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReject(flowTaskVo);
        return "ok";
    }

    @PostMapping(value = "/return")
    public Object taskReturn(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReturn(flowTaskVo);
        return "ok";
    }

    @PostMapping(value = "/returnList")
    public Object findReturnTaskList(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.findReturnTaskList(flowTaskVo);
    }

    @DeleteMapping(value = "/delete")
    public Object delete(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.deleteTask(flowTaskVo);
        return "ok";
    }

    @PostMapping(value = "/claim")
    public Object claim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.claim(flowTaskVo);
        return "ok";
    }

    @PostMapping(value = "/unClaim")
    public Object unClaim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.unClaim(flowTaskVo);
        return "ok";
    }

    @PostMapping(value = "/delegate")
    public Object delegate(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.delegateTask(flowTaskVo);
        return "ok";
    }

    @PostMapping(value = "/assign")
    public Object assign(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.assignTask(flowTaskVo);
        return "ok";
    }

    // @PostMapping(value = "/nextFlowNode")
    // public Object getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
    //     return flowTaskService.getNextFlowNode(flowTaskVo);
    // }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) {
        InputStream inputStream = flowTaskService.diagram(processId);
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 生成流程图
     *
     * @param procInsId 任务ID
     */
    @RequestMapping("/flowViewer/{procInsId}")
    public Object getFlowViewer(@PathVariable("procInsId") String procInsId) {
        return flowTaskService.getFlowViewer(procInsId);
    }
}
