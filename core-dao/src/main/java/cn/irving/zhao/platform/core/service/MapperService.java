package cn.irving.zhao.platform.core.service;

import cn.irving.zhao.platform.core.dao.CustomMapper;

import javax.annotation.Resource;

public abstract class MapperService<T, M extends CustomMapper<T>> {
    @Resource
    protected M mapper;
}
