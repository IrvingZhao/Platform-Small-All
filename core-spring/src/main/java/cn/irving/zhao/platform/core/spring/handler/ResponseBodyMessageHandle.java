package cn.irving.zhao.platform.core.spring.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.xindi")
public class ResponseBodyMessageHandle implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getMethodAnnotation(ResponseBody.class) != null && MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Map<String, Object> resultValue = new HashMap<>();
        resultValue.put("isSuccess", true);
        resultValue.put("errorMessage", "");
        resultValue.put("data", body);
        return resultValue;
    }

    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> exceptionHandle(Throwable throwable) {
        Map<String, Object> resultValue = new HashMap<>();
        resultValue.put("isSuccess", false);
        resultValue.put("errorMessage", throwable.getMessage());
        return resultValue;
    }
}
