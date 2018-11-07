package com.voronin.english.controller;

import com.voronin.english.domain.User;
import com.voronin.english.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.11.2018.
 */
@RestController
public class OauthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping("/registration")
    public Object registration(@RequestBody final User user) {
        Optional<User> result = this.userService.regUser(user);
        return result.<Object>map(usr -> new Object(){
            public User getUser() {
                return usr;
            }
        }).orElseGet(() -> new Object(){
            public String getError() {
                return String.format("Пользователь с почтой %s уже зарегистрирован.", user.getEmail());
            }
        });
    }

    @RequestMapping("/revoke")
    @ResponseStatus(HttpStatus.OK)
    public void logoutUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }
}
