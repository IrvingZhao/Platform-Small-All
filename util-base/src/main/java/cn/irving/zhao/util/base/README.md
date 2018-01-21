## 基础工具包

> 1. clone - 克隆工具包，被克隆对象需实现 `java.io.Serializable` 或 `java.io.Cloneable` 接口
> 2. exception - 异常信息类，包含需检查异常以及运行时异常基础类，除默认方法外，提供使用`String.format` 进行异常信息格式化
> 3. property - 属性文件加载类，自动加载项目Classpath下所有properties文件，手动加载全部文件、手动加载指定文件方法，后加载文件将覆盖先加载文件内的属性，提供正则匹配key值获取属性
> 4. random - 提供随机生成英文字符串代码
> 5. redis - 基于jedis的二次封装，支持集群配置
> 6. security - 加密方法
>    1. AESSecurity - 提供AES加密方法，包括AES下各种加密算法，默认提供AES、AES_KEY自动生成实例
>    2. CryptoUtils - HMAC 方式进行非对称加密
>    3. MessageDigestSecurity - Hash加密，默认提供MD5，SHA-256，SHA-512实例
> 7. serial - 序列化工具包，提供对象<-->byte，对象<-->字符串，字符串<-->byte间的转换。额外提供 `CustomEnumValue` 接口作为序列化枚举接口使用