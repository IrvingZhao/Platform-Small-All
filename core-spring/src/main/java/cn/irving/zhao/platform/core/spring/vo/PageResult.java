package cn.irving.zhao.platform.core.spring.vo;

import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResult<T> {

    public PageResult(Page<T> page) {
        this.total = page.getTotal();
        this.list = page.getResult();
    }

    private long total;

    private List<T> list;
}
