package org.luvx.fund.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

/**
 * 净值
 */
@Getter
@Setter
@Builder
@ToString
@TableName(value = "fund_data")
public class FundData {
    @Tolerate
    public FundData() {
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String code;
    /**
     * 净值/增长率
     * org.luvx.fund.constant.DataType
     */
    private int type;
    private long x;
    private BigDecimal y;
}
