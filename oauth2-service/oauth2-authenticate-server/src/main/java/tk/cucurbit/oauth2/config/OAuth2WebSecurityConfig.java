package tk.cucurbit.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
@EnableWebSecurity
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final UserDetailsChecker preUserDetailsChecker;
    private final UserDetailsChecker postUserDetailsChecker;

    public OAuth2WebSecurityConfig(
            @Qualifier("OAuth2UserDetailsService") UserDetailsService userDetailsService,
            AuthenticationEntryPoint authenticationEntryPoint,
            UserDetailsChecker preUserDetailsChecker,
            UserDetailsChecker postUserDetailsChecker) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.preUserDetailsChecker = preUserDetailsChecker;
        this.postUserDetailsChecker = postUserDetailsChecker;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                    .and()
                .formLogin()
                    .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint);
    }





    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider usernamePasswordAuthenticationProvider = new DaoAuthenticationProvider();
        usernamePasswordAuthenticationProvider.setUserDetailsService(userDetailsService);
        usernamePasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        usernamePasswordAuthenticationProvider.setHideUserNotFoundExceptions(false);
        usernamePasswordAuthenticationProvider.setPreAuthenticationChecks(preUserDetailsChecker);
        usernamePasswordAuthenticationProvider.setPostAuthenticationChecks(postUserDetailsChecker);
        return usernamePasswordAuthenticationProvider;
    }



}
