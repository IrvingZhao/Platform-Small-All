package cn.irving.zhao.platform.weixin.mp.send.message.material.list;

import cn.irving.zhao.platform.weixin.mp.send.message.material.entity.NewsMaterial;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListMaterialNewsInputMessage extends ListMaterialInputMessage {
    private List<NewsMaterial> item;
}
