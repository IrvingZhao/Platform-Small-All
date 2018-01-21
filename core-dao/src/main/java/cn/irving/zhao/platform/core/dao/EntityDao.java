package cn.irving.zhao.platform.core.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by irvin on 2018/1/21.
 */
public final class EntityDao implements ApplicationContextAware {

    Map<Class, CustomMapper> mapperCache = new HashMap<>();


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, CustomMapper> mappers = applicationContext.getBeansOfType(CustomMapper.class);
        mappers.entrySet().parallelStream().forEach(item -> {
            CustomMapper itemMapper = item.getValue();
            Class entityClass = getMapperEntityClass(itemMapper.getClass());
            if (entityClass != null) {
                mapperCache.put(entityClass, itemMapper);
            }
        });
    }

    private Class getMapperEntityClass(Class mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        for (Type item : types) {
            if (Class.class.isInstance(item) && CustomMapper.class.isAssignableFrom((Class<?>) item)) {
                return getMapperEntityClass((Class) item);
            } else if (ParameterizedType.class.isInstance(item)) {
                ParameterizedType parameterizedType = (ParameterizedType) item;
                return (Class) parameterizedType.getActualTypeArguments()[0];
            }
        }
        return null;
    }


    public int deleteByPrimaryKey(Object key) {
        return 0;
    }


    public int delete(Object record) {
        return 0;
    }


    public int insert(Object record) {
        return 0;
    }


    public int insertSelective(Object record) {
        return 0;
    }


    public boolean existsWithPrimaryKey(Object key) {
        return false;
    }


    public List selectAll() {
        return null;
    }


    public Object selectByPrimaryKey(Object key) {
        return null;
    }


    public int selectCount(Object record) {
        return 0;
    }


    public List select(Object record) {
        return null;
    }


    public Object selectOne(Object record) {
        return null;
    }


    public int updateByPrimaryKey(Object record) {
        return 0;
    }


    public int updateByPrimaryKeySelective(Object record) {
        return 0;
    }


    public int deleteByExample(Object example) {
        return 0;
    }


    public List selectByExample(Object example) {
        return null;
    }


    public int selectCountByExample(Object example) {
        return 0;
    }


    public int updateByExample(@Param("record") Object record, @Param("example") Object example) {
        return 0;
    }


    public int updateByExampleSelective(@Param("record") Object record, @Param("example") Object example) {
        return 0;
    }


    public int deleteByIds(String ids) {
        return 0;
    }


    public List selectByIds(String ids) {
        return null;
    }


    public List selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return null;
    }


    public List selectByRowBounds(Object record, RowBounds rowBounds) {
        return null;
    }
}
