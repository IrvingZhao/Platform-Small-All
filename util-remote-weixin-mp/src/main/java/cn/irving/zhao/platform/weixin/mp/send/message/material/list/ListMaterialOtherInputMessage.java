package cn.irving.zhao.platform.weixin.mp.send.message.material.list;

import cn.irving.zhao.platform.weixin.mp.send.message.material.entity.OtherMaterial;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListMaterialOtherInputMessage extends ListMaterialInputMessage {

    private List<OtherMaterial> item;

}
