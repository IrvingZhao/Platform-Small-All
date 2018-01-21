package cn.irving.zhao.platform.core.freemarker;

import cn.irving.zhao.platform.core.spring.SpringContextUtil;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * 自定义标签创建器
 */

public class TagCreator implements TemplateMethodModelEx {

    public Object exec(List args) throws TemplateModelException {
        String beanid = (String) args.get(0);
        if (StringUtils.isEmpty(beanid)) {
            throw new TemplateModelException("标签beanid参数不能为空");
        }
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();
        return applicationContext.getBean(beanid);
    }

}
