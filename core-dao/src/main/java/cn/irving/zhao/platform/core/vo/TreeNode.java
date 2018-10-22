package cn.irving.zhao.platform.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public abstract class TreeNode<T, U extends TreeNode<T, U>> {

    private String id;
    private String parentId;
    private List<U> children;

    public void setData(List<T> data) {
        Map<String, U> cache = new HashMap<>();
        TreeNode root = this;
        data.stream().map(this::parse).forEach((item) -> cache.put(item.getId(), item));

        cache.forEach((key, value) -> {
            TreeNode parent;
            if (value.getParentId() == null || "".equals(value.getParentId().trim())) {
                parent = root;
            } else {
                parent = cache.get(value.getParentId());
                if (parent == null) {
                    parent = root;
                }
            }
            parent.addChild(value);
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
