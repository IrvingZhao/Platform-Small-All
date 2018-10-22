package cn.irving.zhao.platform.weixin.mp.message.send.material.list;

import cn.irving.zhao.platform.weixin.mp.message.send.material.entity.OtherMaterial;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListMaterialOtherInputMessage extends ListMaterialInputMessage {

    private List<OtherMaterial> item;

}
