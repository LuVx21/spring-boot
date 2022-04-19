package org.luvx.fund.entity;

import java.math.BigDecimal;
import java.util.List;

import org.luvx.fund.constant.CommonKey;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
@ToString
@TableName(value = "fund")
public class Fund {
    @Tolerate
    public Fund() {
    }

    @JSONField(name = CommonKey.KEY_CODE)
    @TableId(value = "code", type = IdType.INPUT)
    private String code;
    @JSONField(name = CommonKey.KEY_NAME)
    private String name;
    @JSONField(name = CommonKey.KEY_YEAR)
    private BigDecimal year;
    @JSONField(name = CommonKey.KEY_MONTH_6)
    private BigDecimal monthSix;
    @JSONField(name = CommonKey.KEY_MONTH_3)
    private BigDecimal monthThree;
    @JSONField(name = CommonKey.KEY_MONTH_1)
    private BigDecimal monthOne;

    @TableField(exist = false)
    @JSONField(name = CommonKey.KEY_WORTH)
    private List<List<BigDecimal>> worth;
    @TableField(exist = false)
    @JSONField(name = CommonKey.KEY_GRAND)
    private List<List<BigDecimal>> grand;
}
