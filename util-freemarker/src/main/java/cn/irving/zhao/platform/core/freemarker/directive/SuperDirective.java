//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.irving.zhao.platform.core.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public class SuperDirective implements TemplateDirectiveModel {
    public static final String DIRECTIVE_NAME = "super";

    public SuperDirective() {
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        OverrideDirective.TemplateDirectiveBodyOverrideWraper current = (OverrideDirective.TemplateDirectiveBodyOverrideWraper) env.getVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE);
        if (current == null) {
            throw new TemplateException("<@super/> direction must be child of override", env);
        } else {
            TemplateDirectiveBody parent = current.parentBody;
            if (parent == null) {
                throw new TemplateException("not found parent for <@super/>", env);
            } else {
                parent.render(env.getOut());
            }
        }
    }
}
