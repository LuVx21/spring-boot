package org.luvx.tools.web.shorturl.entity.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("获取长域名实体")
public class GetLongUrlReq {
    @ApiModelProperty("短域名")
    @NotBlank(message = "url不能为空")
    @Size(max = 8)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String shortUrl;
}
