package tk.cucurbit.oauth2.config.permission;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class PermissionSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

    private final AuthenticationManager authenticationManager;
    private final FilterInvocationSecurityMetadataSource securityMetadataSource;
    private final AccessDecisionManager accessDecisionManager;

    public PermissionSecurityConfigurer(
            AccessDecisionManager accessDecisionManager,
            FilterInvocationSecurityMetadataSource securityMetadataSource,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.accessDecisionManager = accessDecisionManager;
        this.securityMetadataSource = securityMetadataSource;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        PermissionFilterInterceptor filter = new PermissionFilterInterceptor(
                this.accessDecisionManager, this.securityMetadataSource, authenticationManager
        );
        http.addFilterAfter(filter, FilterSecurityInterceptor.class);
    }

}
