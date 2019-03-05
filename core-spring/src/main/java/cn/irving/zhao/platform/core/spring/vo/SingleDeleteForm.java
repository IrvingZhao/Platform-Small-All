package cn.irving.zhao.platform.core.spring.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("单个删除")
public class SingleDeleteForm {

    @ApiModelProperty(value = "被删除对象id", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

}
