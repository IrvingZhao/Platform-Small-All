package cn.irving.zhao.platform.core.shiro.resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;

/**
 * 资源权限读取工厂
 */
public class CustomResourceRoleFactory implements FactoryBean<Ini.Section> {

    private static final String permsTemplate = "{0}[{1}]";

    @Autowired
    public CustomResourceRoleFactory(ShiroResourceService resourceService) {
        if (resourceService == null) {
            throw new ShiroException("未找到类型为[com.xindi.platform.core.shiro.resource.ShiroResourceService]的对象");
        }
        this.resourceService = resourceService;
    }

    private final ShiroResourceService resourceService;
    private String filterChainDefinitions;

    @Override
    public Ini.Section getObject() throws Exception {

        List<ShiroResource> resources = resourceService.getResources();
        Ini ini = new Ini();
        if (filterChainDefinitions == null) {
            filterChainDefinitions = "";
        }
        ini.load(filterChainDefinitions);

        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME) == null ? ini.addSection(Ini.DEFAULT_SECTION_NAME) : ini.getSection(Ini.DEFAULT_SECTION_NAME);

        resources.stream().filter((item) -> StringUtils.isNoneBlank(item.getPath())).forEach((resourceItem) -> {

            StringBuilder permissionBuilder = new StringBuilder();

            String[] pathPerms = resourceItem.getPerms().entrySet().stream()
                    .map((permItem) -> MessageFormat.format(permsTemplate, permItem.getKey(), permItem.getValue()).replace("[]", ""))
                    .toArray(String[]::new);//构建

            section.put(resourceItem.getPath(), StringUtils.join(pathPerms, ","));

        });

        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return Ini.Section.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
