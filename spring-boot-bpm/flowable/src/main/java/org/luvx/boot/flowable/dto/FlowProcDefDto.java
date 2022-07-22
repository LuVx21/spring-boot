package org.luvx.boot.flowable.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowProcDefDto implements Serializable {
    private String id;
    private String name;
    private String key;
    private String category;
    // private String formName;
    // private Long   formId;
    private int    version;
    private String deploymentId;
    private int    suspensionState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   deploymentTime;
}