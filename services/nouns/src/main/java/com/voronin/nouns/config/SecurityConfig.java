package com.voronin.nouns.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Config.
 *
 * @author Alexey Voronin.
 * @since 23.08.2019.
 */
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @param web web.
     * @throws Exception exception.
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/image/**",
                "/category/**", "/noun/nouns/**");
    }
}
