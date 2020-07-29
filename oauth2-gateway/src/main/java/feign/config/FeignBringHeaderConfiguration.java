//package feign.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//import java.util.Objects;
//
//@Slf4j
////@Configuration
//public class FeignBringHeaderConfiguration implements RequestInterceptor {
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
//        if (!(attributes instanceof ServletRequestAttributes)) {
//            return;
//        }
//        ServletRequestAttributes servletAttributes = (ServletRequestAttributes) attributes;
//        HttpServletRequest servletRequest = servletAttributes.getRequest();
//        Enumeration<String> enumeration = servletRequest.getHeaderNames();
//        if (Objects.isNull(enumeration)) {
//            return;
//        }
//        while (enumeration.hasMoreElements()) {
//            String headerName = enumeration.nextElement();
//            String headerValue = servletRequest.getHeader(headerName);
//
//            requestTemplate.header(headerName, headerValue);
//        }
//        log.info("Use FeignBringHeaderConfiguration with request header: {}", requestTemplate.headers());
//    }
//}
