## Shiro工具包

### 提供功能

1. <a href="#oauth登陆相关类及配置">oauth登陆</a>
2. <a href="#用户名密码登陆类及配置">用户名密码登陆</a>
3. <a href="#动态资源权限加载器">动态资源权限加载器</a>
4. <a href="#自定义登陆重定向地址">自定义登陆重定向地址</a>
5. <a href="#单资源多过滤器">单资源多过滤器间使用 或 关系</a>



### 提供配置类及配置方式

####用户查询相关接口<a name='用户查询相关接口'> </a>

> `cn.irving.zhao.platform.core.shiro.user.ShiroUserService` 用户信息查询类，包含`getUserInfoByUserName(String username)` 根据用户名查询用户，`getUserInfoByPlatfomInfo(String platform, String platformUserId)` 根据第三方平台编码、第三方授权key或第三方平台用户id查询用户
>
> 继承接口，并复写相关方法，为oauth登录、用户名密码登录提供相关查询方法

#### 自定义ShiroFilterFactory<a name="自定义ShiroFilterFactory"></a>

> ```xml
> <bean id="shiroFilter" class="cn.irving.zhao.platform.core.shiro.CustomShiroFilterFactoryBean">
>   <!-- 是否开启 资源 多权限匹配时，只匹配一个即可通过授权，true为开启，false为不开启，默认为true -->
>   <property name="enableSingleMatch" value="false"/>
>   <!-- 登陆地址生成器，仅限登陆地址生成 -->
>   <property name="urlGenerator" ref="loginUrlGenerator"/>
> </bean>
> ```
>
> 

#### oauth登录相关类及配置<a name="oauth登陆相关类及配置"></a>

##### 提供类

1.  `cn.irving.zhao.platform.core.shiro.realm.OauthAuthorizingRealm` oauth登录处理类
2. `cn.irving.zhao.platform.core.shiro.token.OauthToken` oauth登录token类
3. `cn.irving.zhao.platform.core.shiro.matcher.OauthCredentialsMatcher` oauth登录授权匹配类

##### 使用方式

> 第一步：创建<a href="#用户查询相关接口">用户查询相关接口</a>
>
> 第二步：创建CredentialsMatcher类
>
> ```xml
> <bean id="oauthMatcher" class="cn.irving.zhao.platform.core.shiro.matcher.OauthCredentialsMatcher"/>
> ```
>
> 第三步：创建OauthAuthorizingRealm并指定matcher类
>
> ```xml
> <bean id="oauthRealm" class="cn.irving.zhao.platform.core.shiro.realm.OauthAuthorizingRealm">
>     <property name="credentialsMatcher" ref="oauthMatcher"/>
> </bean>
> ```
>
> 第四步：在SecurityManager中指定realm
>
> ```
> <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
>     <property name="realm" ref="oauthRealm"/>
> </bean>
> ```
>
> 第五步：在ShiroFactoryBean中，指定SecurityManager
>
> ```
> <bean id="shiroFilter" class="cn.irving.zhao.platform.core.shiro.CustomShiroFilterFactoryBean">
>     <property name="securityManager" ref="securityManager"/>
>     <!-- 其他配置信息省略 -->
> </bean>
> ```

#### 用户名密码登陆类及配置<a name="用户名密码登陆类及配置"></a>

##### 提供类

1.  `cn.irving.zhao.platform.core.shiro.realm.PasswordAuthorizingRealm` oauth登录处理类
2. `cn.irving.zhao.platform.core.shiro.token.PasswordToken` oauth登录token类
3. `cn.irving.zhao.platform.core.shiro.matcher.PasswordCredentialsMatcher` oauth登录授权匹配类

> 在使用用户名密码相关登陆时，用户密码的加密算法为：不包含salt - MD5，包含salt - Hamc1，相关工具类：`cn.irving.zhao.util.base.security.MessageDigestSecurity`，`cn.irving.zhao.util.base.security.CryptoUtils` 

##### 使用方法

> 与oauth登陆使用方式一直，需将相关类替换为用户名密码登陆相关类即可

###动态资源权限加载器<a name="动态资源权限加载器"></a>

##### 提供类

1. `cn.irving.zhao.platform.core.shiro.resource.CustomResourceRoleFactory` 资源加载工厂
2. `cn.irving.zhao.platform.core.shiro.resource.ShiroResourceService` 资源加载服务
3. `cn.irving.zhao.platform.core.shiro.resource.ShiroResource` 资源对象

##### 使用方式

> 第一步：实现 资源加载服务 接口，并添加至spring上下文
>
> 第二步：声明 资源加载工厂 对象
>
> ```xml
> <bean id="resourceFactory" class="cn.irving.zhao.platform.core.shiro.resource.CustomResourceRoleFactory"/>
> ```
>
> 第三步：在ShiroFilterFactory中指定 resourceFactory
>
> ```xml
> <bean id="shiroFilter" class="cn.irving.zhao.platform.core.shiro.CustomShiroFilterFactoryBean">
>     <property name="filterChainDefinitionMap" ref="resourceFactory"/>
>   <!-- 省略其他配置信息 -->
> </bean>
> ```

#### 自定义登陆重定向地址 <a name="自定义登陆重定向地址"></a>

##### 提供类

1. `cn.irving.zhao.platform.core.shiro.redirect.LoginUrlGenerator` 登陆地址生成器

##### 使用方法

> 第一步：声明 登陆地址生成器 
>
> 第二步：<a href="#自定义ShiroFilterFactory">使用自定义ShiroFilterFactory对象</a>

#### 单资源多过滤器间使用 或 关系<a name="单资源多过滤器"></a>

##### 提供类

1. `cn.irving.zhao.platform.core.shiro.filters.MultipleRoleAuthFilter` 标记是否启用匹配一个过滤器即可

##### 使用方法

> 第一步：声明 标记过滤器 
>
> ```xml
> <bean id="matchOne" class="cn.irving.zhao.platform.core.shiro.filters.MatchOneFilter"/>
> ```
>
> 第二步：<a href="#自定义ShiroFilterFactory">使用自定义ShiroFilterFactory</a> 并 添加 matchOne filter
>
> ```xml
> <bean id="shiroFilter" class="cn.irving.zhao.platform.core.shiro.CustomShiroFilterFactoryBean">
>   <property name="filters">
>     <map>
>       <entry key="matchOne" value-ref="matchOne"/>
>     </map>
>   </property>
> </bean>
> ```
>
> 第三步：标记指定资源使用单权限匹配
>
> ```xml
> <bean id="shiroFilter" class="cn.irving.zhao.platform.core.shiro.CustomShiroFilterFactoryBean">
>   <property name="filterChainDefinitions">
>     <value>
>     /urla/a = matchOne,其他权限
>     </value>
>   </property>
> </bean>
> ```