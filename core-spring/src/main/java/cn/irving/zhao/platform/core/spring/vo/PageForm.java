package cn.irving.zhao.platform.core.spring.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("分页请求参数")
public class PageForm {

    @ApiModelProperty("每页大小")
    @NotNull
    @Min(1)
    private Integer pageSize = 10;

    @ApiModelProperty("页码")
    @Min(0)
    @NotNull
    private Integer pageIndex = 1;
}
