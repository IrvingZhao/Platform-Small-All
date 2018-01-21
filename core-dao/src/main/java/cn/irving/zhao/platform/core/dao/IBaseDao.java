package cn.irving.zhao.platform.core.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T, PK extends Serializable> {
    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param entity 实体对象
     * @return 操作行数
     */
    int save(T entity);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity 实体对象
     * @return 操作行数
     */
    int saveSelective(T entity);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity 实体对象
     * @return 操作行数
     */
    int update(T entity);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity 实体对象
     * @return 操作行数
     */
    int updateSelective(T entity);

    /**
     * 根据Example条件更新实体record包含的全部属性，null值会被更新
     *
     * @param record  更新对象
     * @param example 更新条件
     * @return 操作行数
     */
    int updateByExample(@Param("record") T record, @Param("example") Object example);

    /**
     * 根据Example条件更新实体record包含的不是null的属性值
     *
     * @param record  更新对象
     * @param example 更新条件
     * @return 操作行数
     */
    int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param entity 实体对象
     * @return 操作行数
     */
    int delete(T entity);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey 主键
     * @return 操作行数
     */
    int deleteById(PK primaryKey);

    /**
     * 根据主键字符串进行删除，ids 如 "1,2,3" 这种形式的字符串
     * <p>方法要求实体类中有且只有一个带有@Id注解的字段，否则会抛出异常。</p>
     *
     * @param ids 多主键字符串
     * @return 操作行数
     */
    int deleteByIds(String ids);

    /**
     * 根据Example条件删除数据
     *
     * @param example 删除条件对象
     * @return 操作行数
     */
    int deleteByExample(Object example);


    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity 查询参数实体对象
     * @return 查询结果
     */
    List<T> select(T entity);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param id 主键
     * @return 对象
     */
    T selectById(PK id);

    /**
     * 查询全部结果，select(null)方法能达到同样的效果
     *
     * @return 查询结果
     */
    List<T> selectAll();

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record 查询参数实体对象
     * @return 返回结果
     */
    T selectOne(T record);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param entity 查询实体
     * @return 统计结果
     */
    int selectCount(T entity);

    /**
     * 根据Example条件进行查询
     * <p>重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列</p>
     *
     * @param example 查询条件对象
     * @return 查询结果
     */
    List<T> selectByExample(Object example);

    /**
     * 根据Example条件进行查询总数
     *
     * @param example 查询条件对象
     * @return 总条数
     */
    int selectCountByExample(Object example);

    /**
     * 根据主键字符串进行查询，ids 如 "1,2,3" 这种形式的字符串
     * <p>方法要求实体类中有且只有一个带有@Id注解的字段，否则会抛出异常。</p>
     *
     * @param ids 多主键字符串
     * @return 查询结果
     */
    List<T> selectByIds(String ids);

    /**
     * 分页查询全部
     *
     * @param pageIndex 页索引，从1开始
     * @param pageSize  也大小
     * @return 分页对象
     */
    PageInfo<T> selectPage(int pageIndex, int pageSize);

    /**
     * 根据条件查询分页
     *
     * @param pageIndex 页索引，从1开始
     * @param pageSize  也大小
     * @param example   查询条件
     * @return 分页对象
     */
    PageInfo<T> selectPageByExample(int pageIndex, int pageSize, Object example);

}
