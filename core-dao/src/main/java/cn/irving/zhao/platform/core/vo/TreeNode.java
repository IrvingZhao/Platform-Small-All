package cn.irving.zhao.platform.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树节点工具类，提供基础的id，parentId，children内容，可额外添加其他属性
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class TreeNode<T, U extends TreeNode<T, U>> {

    private String id;
    private String parentId;
    private List<U> children;

    /**
     * 设置数据，当数据父节点对象未找到时，忽略父节点
     *
     * @param data 数据列表
     */
    public void setData(List<T> data) {
        this.setData(data, false);
    }

    /**
     * 设置数据项
     *
     * @param data             数据列表
     * @param nullParentToRoot 数据父节点未找到时，是否将其添加至根节点
     */
    public void setData(List<T> data, boolean nullParentToRoot) {
        Map<String, U> cache = new HashMap<>();
        TreeNode<T, U> root = this;
        data.stream().map(this::parse).forEach((item) -> cache.put(item.getId(), item));

        cache.forEach((key, value) -> {
            TreeNode<T, U> parent;
            if (value.getParentId() == null || "".equals(value.getParentId().trim())) {
                parent = root;
            } else {
                parent = cache.get(value.getParentId());
                if (parent == null && nullParentToRoot) {
                    parent = root;
                }
            }
            if (parent != null) {
                parent.addChild(value);
            }
        });
    }

    protected abstract U parse(T item);

    private void addChild(U itemNode) {
        if (children == null) {
            synchronized (this) {
                if (children == null) {
                    children = new ArrayList<>();
                }
            }
        }
        children.add(itemNode);
    }

}
