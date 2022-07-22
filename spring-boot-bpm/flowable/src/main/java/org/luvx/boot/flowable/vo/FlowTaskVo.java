package org.luvx.boot.flowable.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FlowTaskVo {
    private String              taskId;
    private String              userId;
    private String              comment;
    private String              instanceId;
    private String              targetKey;
    private Map<String, Object> values;
    private String              assignee;
    private List<String>        candidateUsers;
    private List<String>        candidateGroups;
}