package com.voronin.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth 2 config class.
 *
 * @author Alexey Voronin.
 * @since 05.11.2018.
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    /**
     * Access token is only valid for 12 hour.
     */
    private final int accessToken = 43200;

    /**
     * Refresh token is only valid for 12 hour.
     */
    private final int refreshToken = 43200;

    /**
     * Client id.
     */
    @Value("${auth.clientId}")
    private String clientId;

    /**
     * Password.
     */
    @Value("${auth.password}")
    private String password;

    /**
     * Authentication manager.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Token store.
     */
    private final TokenStore tokenStore;

    /**
     * BCrypt password encoder.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor.
     *
     * @param authManager authentication manager.
     * @param tokenStore  token store.
     * @param encoder     BCrypt password encoder.
     */
    @Autowired
    public OAuth2Config(
            final AuthenticationManager authManager,
            final TokenStore tokenStore,
            final BCryptPasswordEncoder encoder) {
        this.authenticationManager = authManager;
        this.tokenStore = tokenStore;
        this.passwordEncoder = encoder;
    }

    /**
     * Set token store and authentication manager.
     *
     * @param endpoints Authorisation server endpoints configurer.
     * @throws Exception exception.
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
    }

    /**
     * Config clients details.
     *
     * @param clients Client details service configurer.
     * @throws Exception exception.
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(this.clientId)
                .secret(passwordEncoder.encode(this.password))
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .scopes("openid")
                .accessTokenValiditySeconds(accessToken)
                .refreshTokenValiditySeconds(refreshToken);
    }
}
