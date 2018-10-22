package cn.irving.zhao.platform.core.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CustomMapper<T> extends Mapper<T>, IdsMapper<T> {

    default PageInfo<T> selectPage(int pageIndex, int pageSize) {
        Page<T> result = PageHelper.startPage(pageIndex, pageSize);
        this.selectAll();
        return result.toPageInfo();
    }

    default PageInfo<T> selectPageByExample(int pageIndex, int pageSize, Object example) {
        Page<T> result = PageHelper.startPage(pageIndex, pageSize);
        this.selectByExample(example);
        return result.toPageInfo();
    }

}
