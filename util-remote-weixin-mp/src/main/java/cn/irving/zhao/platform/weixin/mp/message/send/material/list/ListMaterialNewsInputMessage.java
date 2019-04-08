package cn.irving.zhao.platform.weixin.mp.message.send.material.list;

import cn.irving.zhao.platform.weixin.mp.message.send.material.entity.NewsMaterial;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListMaterialNewsInputMessage extends ListMaterialInputMessage {
    private List<NewsMaterial> item;
}
