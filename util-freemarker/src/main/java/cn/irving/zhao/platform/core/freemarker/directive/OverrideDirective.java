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
import java.io.Writer;
import java.util.Map;

public class OverrideDirective implements TemplateDirectiveModel {
    public static final String DIRECTIVE_NAME = "override";

    public OverrideDirective() {
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String name = DirectiveUtils.getRequiredParam(params, "name");
        String overrideVariableName = DirectiveUtils.getOverrideVariableName(name);
        TemplateDirectiveBodyOverrideWraper override = DirectiveUtils.getOverrideBody(env, name);
        TemplateDirectiveBodyOverrideWraper current = new TemplateDirectiveBodyOverrideWraper(body, env);
        if (override == null) {
            env.setVariable(overrideVariableName, current);
        } else {
            DirectiveUtils.setTopBodyForParentBody(env, current, override);
        }

    }

    static class TemplateDirectiveBodyOverrideWraper implements TemplateDirectiveBody, TemplateModel {
        private TemplateDirectiveBody body;
        public TemplateDirectiveBodyOverrideWraper parentBody;
        public Environment env;

        public TemplateDirectiveBodyOverrideWraper(TemplateDirectiveBody body, Environment env) {
            this.body = body;
            this.env = env;
        }

        public void render(Writer out) throws TemplateException, IOException {
            if (this.body != null) {
                TemplateDirectiveBodyOverrideWraper preOverridy = (TemplateDirectiveBodyOverrideWraper) this.env.getVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE);

                try {
                    this.env.setVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE, this);
                    this.body.render(out);
                } finally {
                    this.env.setVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE, preOverridy);
                }

            }
        }
    }
}
