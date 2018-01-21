package cn.irving.zhao.platform.core.freemarker;

import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil.*;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FREEMARKER标签基类
 */
public abstract class BaseFreeMarkerTag implements TemplateMethodModelEx {

    private ObjectStringSerialUtil serialUtil = ObjectStringSerialUtil.getSerialUtil();

    public Object exec(List jsonParam) throws TemplateModelException {

        if (jsonParam != null && !jsonParam.isEmpty()) {
            String param = (String) jsonParam.get(0);
            if (param != null) {
                if (!param.startsWith("{")) {
                    param = "{" + param + "}";
                }
                Map paramObject = serialUtil.parse(param, SerialType.JSON);

                return this.exec(paramObject);
            } else {
                return this.exec(new HashMap());
            }
        } else {
            return this.exec(new HashMap());
        }
    }

    protected abstract Object exec(Map params) throws TemplateModelException;


}
