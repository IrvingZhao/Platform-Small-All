### Spring 工具类

#### Spring上下文工具类

> ``cn.irving.zhao.platform.core.spring.SpringContextUtil`` 类提供SpringApplicationContext中所有的方法的代理，使用时，需在Spring中，声明该类的Bean

#### BindingError 自动异常切片

> ``cn.irving.zhao.platform.core.spring.aspect.BindingErrorControllerAspect`` 提供对使用Spring数据绑定的方法在自动绑定过程中校验的过程中，出现异常情况，则自动抛出异常，异常类型为`cn.irving.zhao.platform.core.spring.exception.CodeUnCheckException`，默认异常码为``100000``，可通过设置类中的``errorCode`` 进行修改。
>
> 使用时，需自行配置切片配置信息

#### ResponseBody响应消息拦截

> `cn.irving.zhao.platform.core.spring.handler.ResponseBodyMessageHandle` 提供ReponseBody消息自动拦截器，提供在返回数据外，自动封装`{"success":true,"code":successCode,msg:"",data:方法返回值}`，其中，`successCode`默认为`0000`，可自行设置。
>
> 在程序出现异常时，`cn.irving.zhao.platform.core.spring.handler.ResponseBodyMessageHandle` 可拦截相关异常信息，当异常为`cn.irving.zhao.platform.core.spring.exception.CodeException` 的子类时，会在响应信息中，将响应信息的`code` 设置为对应值，`msg` 为异常的`getMessage()` 的方法返回值。
>
> 使用时，需在Spring中，声明该类的Bean，并在实际Controller中，实现`cn.irving.zhao.platform.core.spring.controller.ResponseBodyHandleController` 接口

#### 请求日志记录

> `cn.irving.zhao.platform.core.spring.interceptor.LogInterceptor` 提供日志拦截，会输出每次请求的响应时间等信息，格式为`请求耗时：{"url":"{}", "duringTime":{}, "endTime":{}, "handleClass":"{}", "exception":"{}" }` 
>
> - url - 请求地址
> - duringTime - 请求耗时
> - endTime - 请求结束时间
> - handleClass - 执行类
> - exception - 异常信息，Exception..getMessage()
>
> 使用时，需在日志配置文件中，声明 名称为`RequestTimeLogger` 的Logger对象