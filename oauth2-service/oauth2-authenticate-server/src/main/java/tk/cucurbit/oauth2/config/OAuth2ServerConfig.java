package tk.cucurbit.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import tk.cucurbit.oauth2.config.tokenservices.Oauth2CASTokenService;
import tk.cucurbit.oauth2.service.OAuth2ClientDetailsService;

import java.util.Collections;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private final OAuth2WebResponseExceptionTranslator exceptionTranslator;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    private final OAuth2ClientDetailsService clientDetailsService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final TokenStore redisTokenStore;
    private final TokenEnhancer tokenEnhancer;


    public OAuth2ServerConfig(
            OAuth2ClientDetailsService clientDetailsService,
            AuthenticationManager authenticationManager,
            OAuth2WebResponseExceptionTranslator exceptionTranslator,
            AuthenticationEntryPoint authenticationEntryPoint,
            TokenStore redisTokenStore,
            TokenEnhancer tokenEnhancer,
            @Qualifier("OAuth2UserDetailsService") UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder, AccessDeniedHandler accessDeniedHandler){
        this.clientDetailsService = clientDetailsService;
        this.authenticationManager = authenticationManager;
        this.exceptionTranslator = exceptionTranslator;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.redisTokenStore = redisTokenStore;
        this.tokenEnhancer = tokenEnhancer;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .passwordEncoder(passwordEncoder);
        ;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.exceptionTranslator(exceptionTranslator)
                .tokenStore(redisTokenStore)
                .tokenEnhancer(tokenEnhancer)
                .tokenServices(tokenService())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailsService);
        tokenConverter.setUserTokenConverter(userAuthenticationConverter);
        return tokenConverter;
    }


    @Bean
    public Oauth2CASTokenService tokenService() {
        Oauth2CASTokenService tokenService = new Oauth2CASTokenService();
        tokenService.setTokenStore(redisTokenStore);
        tokenService.setSupportRefreshToken(true);
        tokenService.setReuseRefreshToken(true);
        tokenService.setClientDetailsService(clientDetailsService);
        tokenService.setTokenEnhancer(null);
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
            tokenService.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        }
        return tokenService;
    }

}
