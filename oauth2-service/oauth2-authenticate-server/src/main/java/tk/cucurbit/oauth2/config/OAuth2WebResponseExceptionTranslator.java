package tk.cucurbit.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import tk.cucurbit.oauth2.annotations.IgnoreResponseAdvice;
import tk.cucurbit.oauth2.enums.ResponseCode;
import tk.cucurbit.oauth2.exceptions.GlobalOAuth2Exception;

import java.util.Objects;

@Slf4j
@Component
public class OAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    @IgnoreResponseAdvice
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        log.error("authenticate server, WebResponseExceptionTranslator handle exception ", e);
        Throwable[] chain = throwableAnalyzer.determineCauseChain(e);

        Throwable firstThrowableOfType = throwableAnalyzer.getFirstThrowableOfType(UsernameNotFoundException.class, chain);
        if (Objects.nonNull(firstThrowableOfType)) {
            return handleOAuth2Exception(HttpStatus.OK, ResponseCode.OAUTH2_USER_NOT_FOUND.getCode(),
                    "User not found");
        }

        firstThrowableOfType = throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, chain);
        if (Objects.nonNull(firstThrowableOfType)) {
            /*
             * ex.getMessage() may be
             * 1. User account is locked
             * 2. User is disabled
             * 3. User account has expired
             * 4. Bad credentials
             * AbstractUserDetailsAuthenticationProvider$DefaultPreAuthenticationChecks#check(UserDetails)
             *
             * if need modify, goto OAuth2WebSecurityConfig#authenticationProvider,
             * before return, implements UserDetailsChecker and set to authenticationProvider.
             */
            return handleOAuth2Exception(HttpStatus.OK, ResponseCode.OAUTH2_UNAUTHORIZED.getCode(),
                    firstThrowableOfType.getMessage());
        }

        firstThrowableOfType = throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, chain);
        if (Objects.nonNull(firstThrowableOfType)) {
            // 401
            return handleOAuth2Exception(HttpStatus.OK, ResponseCode.OAUTH2_UNAUTHORIZED.getCode(),
                    firstThrowableOfType.getMessage());
        }

        firstThrowableOfType = throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, chain);
        if (Objects.nonNull(firstThrowableOfType)) {
            // 403
            return handleOAuth2Exception(HttpStatus.OK, ResponseCode.OAUTH2_FORBIDDEN.getCode(),
                    firstThrowableOfType.getMessage());
        }

        firstThrowableOfType = throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, chain);
        if (Objects.nonNull(firstThrowableOfType)) {
            // 405
            return handleOAuth2Exception(HttpStatus.OK, ResponseCode.OAUTH2_METHOD_NOT_ALLOW.getCode(),
                    ResponseCode.OAUTH2_METHOD_NOT_ALLOW.getMsg());
        }

        // server error
        return handleOAuth2Exception(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.OAUTH2_SERVER_ERROR.getCode(),
                ResponseCode.OAUTH2_SERVER_ERROR.getMsg());
    }


    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(HttpStatus httpStatus, int errorCode, String msg) {
        OAuth2Exception ex = new GlobalOAuth2Exception(errorCode, msg);
        return new ResponseEntity<>(ex, httpStatus);
    }

}
