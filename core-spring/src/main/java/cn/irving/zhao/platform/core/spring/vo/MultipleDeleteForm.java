package cn.irving.zhao.platform.core.spring.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("根据id批量删除")
@Getter
@Setter
public class MultipleDeleteForm {

    @ApiModelProperty("对象id")
    private List<String> ids;

}
