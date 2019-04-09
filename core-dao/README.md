### 数据库操作核心类

> 基于``tkMapper`` 提供自动生成单表Mapper类，具体Mapper类需继承``cn.irving.zhao.platform.core.dao.CustomMapper``，而后配置相关Mapper类扫描目录，具体配置可参照 [tkMapper](https://github.com/abel533/Mapper)

> 额外提供基础dao实现类，并自动注入Mapper实例，需使用Spring4+

> 提供是否枚举、删除标志位基础实体类、最后更新时间枚举类