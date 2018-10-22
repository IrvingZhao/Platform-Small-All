package cn.irving.zhao.platform.core.entity;

import cn.irving.zhao.platform.core.constract.TrueFalseEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class DFlagEntity {
    @Column(name = "d_flag")
    private TrueFalseEnum dFlag;
}
