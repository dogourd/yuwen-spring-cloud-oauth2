package tk.cucurbit.oauth2.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tk.cucurbit.oauth2.annotations.IgnoreResponseAdvice;
import tk.cucurbit.oauth2.enums.ResponseCode;
import tk.cucurbit.oauth2.vo.CommonResponse;

@RestControllerAdvice
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 方法标记忽略统一响应
        boolean methodDeclaredIgnoreAdvice = returnType.hasMethodAnnotation(IgnoreResponseAdvice.class);
        // 类标记忽略统一响应
        boolean classDeclaredIgnoreAdvice = returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class);
        // 方法返回类型标记忽略统一响应
        boolean returnObjIgnoreAdvice = returnType.getMethod().getReturnType().isAnnotationPresent(IgnoreResponseAdvice.class);
        return !returnObjIgnoreAdvice && !classDeclaredIgnoreAdvice && !methodDeclaredIgnoreAdvice;
    }

    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {


        CommonResponse<Object> commonResponse = new CommonResponse<>(
                ResponseCode.OAUTH2_SUCCESS.getCode(), ResponseCode.OAUTH2_SUCCESS.getMsg()
        );

        if (body == null) {
            return commonResponse;
        } else if (body instanceof CommonResponse) {
            commonResponse = (CommonResponse<Object>) body;
        } else if (body instanceof Exception) {
            // while webResponseExceptionTranslator handle Exception and return ResponseEntity<Oauth2Exception>
            return body;
        } else {
            commonResponse.setData(body);
        }
        return commonResponse;
    }
}
