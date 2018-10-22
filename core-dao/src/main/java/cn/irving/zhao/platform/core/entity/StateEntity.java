package cn.irving.zhao.platform.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class StateEntity extends DFlagEntity {

    @Column(name = "creator")
    private String creator;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modifier")
    private String modifier;
    @Column(name = "modify_time")
    private Date modifyTime;
}
