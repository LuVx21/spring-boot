package org.luvx.boot.flowable.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class FlowTaskDto implements Serializable {
    private String         taskId;
    private String         taskName;
    private String         taskDefKey;
    private Long           assigneeId;
    private String         deptName;
    private String         startDeptName;
    private String         assigneeName;
    private String         startUserId;
    private String         startUserName;
    private String         category;
    private Object         procVars;
    private Object         taskLocalVars;
    private String         deployId;
    private String         procDefId;
    private String         procDefKey;
    private String         procDefName;
    private int            procDefVersion;
    private String         procInsId;
    private String         hisProcInsId;
    private String         duration;
    private FlowCommentDto comment;
    private String         candidate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date           createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date           finishTime;
}
