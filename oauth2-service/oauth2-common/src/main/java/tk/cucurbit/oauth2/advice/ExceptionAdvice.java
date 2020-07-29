package tk.cucurbit.oauth2.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import tk.cucurbit.oauth2.enums.ResponseCode;
import tk.cucurbit.oauth2.exceptions.OAuth2Exception;
import tk.cucurbit.oauth2.vo.CommonResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * spring.mvc.throw-exception-if-no-handler-found: true
     * spring.resources.add-mappings: false
     * */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public CommonResponse<String> handleNoHandlerFound(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("Request Error Uri: {}", request.getRequestURI(), e);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(ResponseCode.OAUTH2_NOTFOUND.getCode());
        commonResponse.setMsg(ResponseCode.OAUTH2_NOTFOUND.getMsg());
        commonResponse.setData(null);
        return commonResponse;
    }

    /**
     * upload size too large
     * */
    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public CommonResponse<String> handleUploadSizeToLarge(HttpServletRequest request, MaxUploadSizeExceededException e) {
        log.error("Request Error Uri: {}", request.getRequestURI(), e);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(411113);
        commonResponse.setData("File To Large");
        return commonResponse;
    }

    /**
     * type miss match
     * */
    @ExceptionHandler(value = {TypeMismatchException.class})
    public CommonResponse<String> handleTypeMismatch(HttpServletRequest request, TypeMismatchException e) {
        log.error("Request Error Uri: {}", request.getRequestURI(), e);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(400001);
        commonResponse.setData("Type Miss Match");
        return commonResponse;
    }

    /**
     * http message not readable
     * */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public CommonResponse<String> handleMessageNotReadable(HttpServletRequest request, HttpMessageNotReadableException e) {
        log.error("Request Error Uri: {}", request.getRequestURI(), e);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(400001);
        commonResponse.setData("Http Message Not Readable.");
        return commonResponse;
    }

    /**
     * todo
     * {@link org.springframework.web.bind.MissingServletRequestParameterException}
     * {@link org.springframework.web.method.annotation.MethodArgumentTypeMismatchException}
     * {@link javax.servlet.ServletException}
     * business exception.
     * */




    /**
     * spring security
     * */
    @ExceptionHandler(value = {OAuth2Exception.class})
    public CommonResponse<String> handleOAuth2Exception(OAuth2Exception oauth2Exception, HttpServletRequest request) {
        log.error("Request Error Uri: {}", request.getRequestURI(), oauth2Exception);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(oauth2Exception.getErrorCode());
        commonResponse.setMsg(oauth2Exception.getMsg());
        return commonResponse;
    }



    /**
     * validation exception methodArgumentNotValidException
     * */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse<List<String>> methodArgumentNotValid(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("Request Error Uri: {}", request.getRequestURI(), ex);
        CommonResponse<List<String>> commonResponse = new CommonResponse<>();
        List<String> errorMsg = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        commonResponse.setCode(400001);
        commonResponse.setData(errorMsg);
        return commonResponse;
    }


    /**
     * validation exception constraint violation exception.
     * */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public CommonResponse<Map<String, String>> handleConstraintViolation(HttpServletRequest request, ConstraintViolationException e) {
        log.error("Request Error Uri: {}", request.getRequestURI(), e);
        Map<String, String> data = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        constraintViolation -> StringUtils.lowerCase(constraintViolation.getPropertyPath().toString()),
                        ConstraintViolation::getMessage
                ));
        CommonResponse<Map<String, String>> commonResponse = new CommonResponse<>();
        commonResponse.setCode(400001);
        commonResponse.setData(data);
        return commonResponse;
    }


    /**
     * reveal all the details
     * */
    @ExceptionHandler(value = {Throwable.class})
    public CommonResponse<String> handleThrowable(Throwable throwable, HttpServletRequest request) {
        log.error("Request Error Uri: {}", request.getRequestURI(), throwable);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(ResponseCode.OAUTH2_SERVER_ERROR.getCode());
        commonResponse.setMsg("error");
        return commonResponse;
    }
}
