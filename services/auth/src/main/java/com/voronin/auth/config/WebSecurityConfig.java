package com.voronin.auth.config;


import com.voronin.auth.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.sql.DataSource;

/**
 * Web security config.
 *
 * @author Alexey Voronin.
 * @since 27.10.2018.
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * BCrypt password encoder.
     *
     * @return BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Implementation user detail service.
     */
    @Autowired
    private DetailService detailService;


    /**
     * Set detail service and password encoder by authentication manager builder.
     *
     * @param builder authentication manager builder.
     * @throws Exception exception.
     */
    @Autowired
    protected void configureGlobal(final AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(detailService)
                .passwordEncoder(encoder());
    }

    /**
     * DataSource.
     */
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    /**
     * Token store bean.
     *
     * @return in memory token store.
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * Authentication manager bean.
     *
     * @return authentication manager bean.
     * @throws Exception exception.
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * HttpFirewall bean.
     *
     * @return HttpFirewall.
     */
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall fireWall = new StrictHttpFirewall();
        fireWall.setAllowUrlEncodedSlash(true);
        return fireWall;
    }

    /**
     * @param web WebSecurity.
     * @throws Exception exception.
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/registration",
                "/activate/**",
                "/revoke",
                "/word/**",
                "/part-of-speech/**",
                "/category/**",
                "/phrase/**",
                "/image/**");
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }
}
