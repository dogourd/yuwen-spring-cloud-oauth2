package tk.cucurbit.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import tk.cucurbit.oauth2.config.permission.PermissionAccessDecisionManager;
import tk.cucurbit.oauth2.config.permission.PermissionSecurityConfigurer;
import tk.cucurbit.oauth2.config.permission.PermissionSecurityMetadataSource;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;


    private final AuthenticationManager authenticationManager;
    private final PermissionAccessDecisionManager accessDecisionManager;
    private final PermissionSecurityMetadataSource securityMetadataSource;

    public OAuth2ResourceConfig(AuthenticationManager authenticationManager,
                                PermissionAccessDecisionManager accessDecisionManager,
                                PermissionSecurityMetadataSource securityMetadataSource, AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler) {
        this.authenticationManager = authenticationManager;
        this.accessDecisionManager = accessDecisionManager;
        this.securityMetadataSource = securityMetadataSource;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        PermissionSecurityConfigurer permissionConfigurer = new PermissionSecurityConfigurer(
                accessDecisionManager, securityMetadataSource, authenticationManager);
        http.apply(permissionConfigurer);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationManager(authenticationManager);
    }
}
