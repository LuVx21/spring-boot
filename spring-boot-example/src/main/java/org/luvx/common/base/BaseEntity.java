package org.luvx.common.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.common.base
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/11 14:50
 */
@Data
public class BaseEntity implements Serializable {
    @TableLogic
    private boolean       deleted;
    @Version
    private int           version;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String        createBy;
    private String        updateBy;
}