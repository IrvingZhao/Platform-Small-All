package cn.irving.zhao.platform.core.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResult<T> {

    private long total;

    private List<T> list;
}
