package cn.irving.zhao.platform.core.freemarker.tag;

/**
 */
public class LacksPermissionTag extends PermissionTag {
    protected boolean showTagBody(String p) {
        return !isPermitted(p);
    }
}
