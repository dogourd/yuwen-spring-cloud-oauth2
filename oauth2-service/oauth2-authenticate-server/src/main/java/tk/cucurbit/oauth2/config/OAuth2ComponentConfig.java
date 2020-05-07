package tk.cucurbit.oauth2.config;

import com.alibaba.fastjson.JSON;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import tk.cucurbit.oauth2.entity.OAuth2User;
import tk.cucurbit.oauth2.enums.ResponseCode;
import tk.cucurbit.oauth2.vo.CommonResponse;

@Slf4j
@Configuration
public class OAuth2ComponentConfig {

    private final StringRedisTemplate redisTemplate;

    public OAuth2ComponentConfig(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.error("authenticate server. authenticationEntryPoint, handle authException ", authException);
            // 401
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            CommonResponse<Object> commonResponse = new CommonResponse<>(
                    ResponseCode.OAUTH2_UNAUTHORIZED.getCode(), ResponseCode.OAUTH2_UNAUTHORIZED.getMsg()
            );
            response.getWriter().write(JSON.toJSONString(commonResponse));
        };
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.error("authenticate server. accessDeniedHandler, handle accessDeniedException ", accessDeniedException);
            // 403
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            CommonResponse<Object> commonResponse = new CommonResponse<>(
                    ResponseCode.OAUTH2_FORBIDDEN.getCode(), ResponseCode.OAUTH2_FORBIDDEN.getMsg()
            );
            response.getWriter().write(JSON.toJSONString(commonResponse));
        };
    }


    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisTemplate.getConnectionFactory());
        tokenStore.setPrefix("oauth2:");
        return tokenStore;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            OAuth2User user = (OAuth2User) authentication.getUserAuthentication().getPrincipal();
            Map<String, Object> additionalInfo = new HashMap<>(8);
            additionalInfo.put("username", user.getUsername());

            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
            token.setAdditionalInformation(additionalInfo);
            return token;
        };
    }


    @Bean(name = "preUserDetailsChecker")
    public UserDetailsChecker preUserDetailsChecker() {
        return user -> {
            if (!user.isAccountNonLocked()) {
                throw new LockedException("账户已被锁定");
            }
            if (!user.isEnabled()) {
                throw new DisabledException("账户未启用");
            }
            if (!user.isAccountNonExpired()) {
                throw new AccountExpiredException("账户已过期");
            }
        };
    }

    @Bean(name = "postUserDetailsChecker")
    public UserDetailsChecker postUserDetailsChecker() {
        return user -> {
            if (!user.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("凭证已过期");
            }
        };
    }


}
