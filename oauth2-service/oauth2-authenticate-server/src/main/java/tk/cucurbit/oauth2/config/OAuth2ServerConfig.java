package tk.cucurbit.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import tk.cucurbit.oauth2.service.OAuth2ClientDetailsService;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private final OAuth2WebResponseExceptionTranslator exceptionTranslator;
    private final OAuth2ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final TokenEnhancer tokenEnhancer;

    public OAuth2ServerConfig(OAuth2ClientDetailsService clientDetailsService, AuthenticationManager authenticationManager, OAuth2WebResponseExceptionTranslator exceptionTranslator, AuthenticationEntryPoint authenticationEntryPoint, TokenStore tokenStore, TokenEnhancer tokenEnhancer, PasswordEncoder passwordEncoder) {
        this.clientDetailsService = clientDetailsService;
        this.authenticationManager = authenticationManager;
        this.exceptionTranslator = exceptionTranslator;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.tokenStore = tokenStore;
        this.tokenEnhancer = tokenEnhancer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.exceptionTranslator(exceptionTranslator)
                .pathMapping("/oauth/token", "/oauth2/token")
                .tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancer)
                .authenticationManager(authenticationManager);
    }






//    @Bean(name = "clientAuthenticationManager")
//    public AuthenticationManager clientAuthenticationManager() {
//        return authentication -> {
//            String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
//                    : authentication.getName();
//            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
//            if (clientDetails == null) {
//                throw new BadCredentialsException("认证服务器未找到客户端应用注册信息");
//            }
//            String presentedPassword = authentication.getCredentials().toString();
//            if (!passwordEncoder.matches(presentedPassword, clientDetails.getClientSecret())) {
//                throw new BadCredentialsException("客户端应用凭证错误");
//            }
//            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
//                    clientDetails, authentication.getCredentials(), clientDetails.getAuthorities());
//            result.setDetails(authentication.getDetails());
//
//            return result;
//        };
//    }

}
