package com.voronin.auth.controller;


import com.voronin.auth.domain.Role;
import com.voronin.auth.domain.User;
import com.voronin.auth.service.RoleService;
import com.voronin.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Oauth controller.
 *
 * @author Alexey Voronin.
 * @since 07.11.2018.
 */
@RestController
public class OauthController {

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Role service.
     */
    private final RoleService roleService;

    /**
     * Token store.
     */
    private final TokenStore tokenStore;

    /**
     * Constructor.
     *
     * @param userService user service.
     * @param roleService role service.
     * @param tokenStore  token store.
     */
    @Autowired
    public OauthController(
            final UserService userService,
            final RoleService roleService,
            final TokenStore tokenStore) {
        this.userService = userService;
        this.roleService = roleService;
        this.tokenStore = tokenStore;
    }

    /**
     * Return current user.
     *
     * @param user user.
     * @return user or null if user is not authorise.
     */
    @RequestMapping("/user/current")
    public Principal getUserById(final Principal user) {
        return user;
    }

    /**
     * Get user id by email.
     *
     * @param principal user .
     * @return user id.
     */
    @RequestMapping("/user")
    public User getUserId(final Principal principal) {
        return this.userService.getUserByEmail(principal.getName());
    }

    /**
     * Registration new user.
     *
     * @param user new user.
     * @return user or error message if a user with this name is already registered.
     */
    @RequestMapping("/registration")
    public Object registration(@RequestBody final User user) {
        Optional<User> result = this.userService.regUser(user);
        return result.<Object>map(usr -> new Object() {
            public User getUser() {
                return usr;
            }
        }).orElseGet(() -> new Object() {
            public String getError() {
                return String.format("Пользователь с почтой %s уже зарегистрирован.", user.getEmail());
            }
        });
    }

    /**
     * Activate user by key.
     *
     * @param key user key.
     * @return User.
     */
    @RequestMapping("/activate/{key}")
    public String activateUser(final @PathVariable String key) {
        return String.format("Activation is %s", this.userService.activateUser(key).isActive());
    }

    /**
     * Logout.
     *
     * @param request http servlet request.
     */
    @RequestMapping("/revoke")
    @ResponseStatus(HttpStatus.OK)
    public void logoutUser(final HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }

    /**
     * Get all users.
     *
     * @return list of User.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/users")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    /**
     * Return all roles.
     *
     * @return list of Role.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/roles")
    public List<Role> getRoles() {
        return this.roleService.getRoles();
    }

    /**
     * Change the user role.
     *
     * @param userId User.
     * @param roleId Role.
     * @return the user for whom the role was changed.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/change-role")
    public User changeRole(final @RequestParam UUID userId, final @RequestParam String roleId) {
        return userService.changeUserRole(userId, UUID.fromString(roleId));
    }
}
