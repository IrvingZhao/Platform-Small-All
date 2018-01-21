//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.irving.zhao.platform.core.freemarker.directive;

import cn.irving.zhao.platform.core.freemarker.directive.OverrideDirective.TemplateDirectiveBodyOverrideWraper;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class DirectiveUtils {
    public static String BLOCK = "__ftl_override__";
    public static String OVERRIDE_CURRENT_NODE = "__ftl_override_current_node";

    public DirectiveUtils() {
    }

    public static String getOverrideVariableName(String name) {
        return BLOCK + name;
    }

    public static void exposeRapidMacros(Configuration conf) {
        conf.setSharedVariable("block", new BlockDirective());
        conf.setSharedVariable("extends", new ExtendsDirective());
        conf.setSharedVariable("override", new OverrideDirective());
        conf.setSharedVariable("super", new SuperDirective());
    }

    static String getRequiredParam(Map params, String key) throws TemplateException {
        Object value = params.get(key);
        if (value != null && !StringUtils.isEmpty(value.toString())) {
            return value.toString();
        } else {
            throw new TemplateModelException("not found required parameter:" + key + " for directive");
        }
    }

    static String getParam(Map params, String key, String defaultValue) throws TemplateException {
        Object value = params.get(key);
        return value == null ? defaultValue : value.toString();
    }

    static OverrideDirective.TemplateDirectiveBodyOverrideWraper getOverrideBody(Environment env, String name) throws TemplateModelException {
        TemplateDirectiveBodyOverrideWraper value = (TemplateDirectiveBodyOverrideWraper) env.getVariable(getOverrideVariableName(name));
        return value;
    }

    static void setTopBodyForParentBody(Environment env, TemplateDirectiveBodyOverrideWraper topBody, TemplateDirectiveBodyOverrideWraper overrideBody) {
        TemplateDirectiveBodyOverrideWraper parent;
        for (parent = overrideBody; parent.parentBody != null; parent = parent.parentBody) ;

        parent.parentBody = topBody;
    }
}
