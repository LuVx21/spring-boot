package org.luvx.boot.flowable.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.luvx.boot.flowable.service.FlowInstanceService;
import org.luvx.boot.flowable.vo.FlowTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/flowable/inst")
public class FlowInstanceController {

    @Autowired
    private FlowInstanceService flowInstanceService;

    @PostMapping("/start")
    public Object start(@RequestParam(defaultValue = "") String procDefId,
                        @RequestParam(defaultValue = "") String procDefKey,
                        @RequestBody Map<String, Object> variables) {
        if (StringUtils.isAllBlank(procDefId, procDefKey)) {
            throw new RuntimeException("定义id 和 key 必须指定一个");
        }
        return flowInstanceService.startProcessInstanceById(procDefId, procDefKey, variables);
    }

    @GetMapping("/list")
    public Object queryListByInstanceId(@RequestParam(defaultValue = "") String instanceId,
                                        @RequestParam(defaultValue = "") String userId) {
        return flowInstanceService.queryListByInstanceId(instanceId, userId);
    }

    @PostMapping(value = "/updateState")
    public Object updateState(@RequestParam Integer state,
                              @RequestParam String instanceId) {
        flowInstanceService.updateState(state, instanceId);
        return "ok";
    }

    @PostMapping(value = "/stopProcessInstance")
    public Object stopProcessInstance(@RequestBody FlowTaskVo flowTaskVo) {
        flowInstanceService.stopProcessInstance(flowTaskVo);
        return "ok";
    }

    @DeleteMapping(value = "/delete")
    public Object delete(@RequestParam String instanceId, @RequestParam(defaultValue = "") String deleteReason) {
        flowInstanceService.delete(instanceId, deleteReason);
        return "ok";
    }
}