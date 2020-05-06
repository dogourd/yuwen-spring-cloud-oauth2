package tk.cucurbit.oauth2.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tk.cucurbit.oauth2.enums.ResponseCode;
import tk.cucurbit.oauth2.exceptions.OAuth2Exception;
import tk.cucurbit.oauth2.vo.CommonResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {


    @ExceptionHandler(value = {OAuth2Exception.class})
    public CommonResponse<String> handleOAuth2Exception(OAuth2Exception oauth2Exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("Request Error Uri: {}", request.getRequestURI(), oauth2Exception);
        response.setStatus(oauth2Exception.getHttpStatus());
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(oauth2Exception.getErrorCode());
        commonResponse.setMsg(oauth2Exception.getMsg());
        return commonResponse;
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> handleThrowable(Throwable throwable, HttpServletRequest request) {
        log.error("Request Error Uri: {}", request.getRequestURI(), throwable);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(ResponseCode.OAUTH2_SERVER_ERROR.getCode());
        commonResponse.setMsg("error");
        return commonResponse;
    }

}
