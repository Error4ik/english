package com.voronin.english.config;

import com.voronin.english.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Web security config.
 *
 * @author Alexey Voronin.
 * @since 27.10.2018.
 */
@Configuration
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
}
