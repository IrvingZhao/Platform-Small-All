package cn.irving.zhao.platform.core.dao.impl;

import cn.irving.zhao.platform.core.dao.CustomMapper;
import cn.irving.zhao.platform.core.dao.IBaseDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDaoImpl<T, PK extends Serializable, M extends CustomMapper<T>> implements IBaseDao<T, PK> {

    @Resource
    protected M mapper;

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int saveSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int update(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateSelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByExample(T record, Object example) {
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByExampleSelective(T record, Object example) {
        return mapper.updateByExampleSelective(record, example);
    }


    @Override
    public int delete(T entity) {
        return mapper.delete(entity);
    }

    @Override
    public int deleteById(PK primaryKey) {
        return mapper.deleteByPrimaryKey(primaryKey);
    }

    @Override
    public int deleteByIds(String ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    public int deleteByExample(Object example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public T selectById(PK o) {
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<T> selectByExample(Object o) {
        return mapper.selectByExample(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return mapper.selectCountByExample(o);
    }

    @Override
    public List<T> selectByIds(String s) {
        return mapper.selectByIds(s);
    }

    @Override
    public PageInfo<T> selectPage(int pageIndex, int pageSize) {
        Page<T> result = PageHelper.startPage(pageIndex, pageSize);
        mapper.selectAll();
        return result.toPageInfo();
    }

    @Override
    public PageInfo<T> selectPageByExample(int pageIndex, int pageSize, Object example) {
        Page<T> result = PageHelper.startPage(pageIndex, pageSize);
        mapper.selectByExample(example);
        return result.toPageInfo();
    }
}
