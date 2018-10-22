package cn.irving.zhao.platform.core.spring.handler;

import cn.irving.zhao.platform.core.spring.controller.ResponseBodyHandleController;
import cn.irving.zhao.platform.core.spring.exception.CodeException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(assignableTypes = ResponseBodyHandleController.class)
@RestControllerAdvice(assignableTypes = ResponseBodyHandleController.class)
public class ResponseBodyMessageHandle implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return (returnType.getMember().getDeclaringClass().getAnnotation(RestController.class) != null ||
                returnType.getMethodAnnotation(ResponseBody.class) != null)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Map<String, Object> resultValue = new HashMap<>();
        resultValue.put("success", true);
        resultValue.put("code", "000000");
        resultValue.put("msg", "");
        resultValue.put("data", body);
        return resultValue;
    }

    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> exceptionHandle(Throwable throwable) {
        Map<String, Object> resultValue = new HashMap<>();
        resultValue.put("success", false);
        if (throwable instanceof CodeException) {
            CodeException codeException = (CodeException) throwable;
            resultValue.put("code", codeException.getCode());
        } else {
            resultValue.put("code", "100000");
        }
        resultValue.put("msg", throwable.getMessage());
        return resultValue;
    }
}
