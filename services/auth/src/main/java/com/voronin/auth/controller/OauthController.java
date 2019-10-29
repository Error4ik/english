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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
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
    public User registration(@RequestBody final User user) {
        return this.userService.regUser(user);
    }

    /**
     * Activate user by key.
     *
     * @param key user key.
     * @return Message if user activate or throw exception.
     */
    @RequestMapping("/activate/{key}")
    public String activateUser(final @PathVariable String key) {
        return this.userService.activateUser(key);
    }

    /**
     * Change update user email.
     *
     * @param principal current principal.
     * @param email     new user email.
     * @param password  user password.
     * @return Changed user.
     */
    @RequestMapping("/update-email")
    public User updateEmail(
            final Principal principal,
            @RequestParam final String email,
            @RequestParam final String password) {
        return this.userService.updateEmail(principal, email, password);
    }

    /**
     * Change user password.
     *
     * @param principal current user.
     * @param oldPass   old user password.
     * @param newPass   new user password.
     * @return User or error if the old password is incorrect.
     */
    @RequestMapping("/update-password")
    public User updatePassword(final Principal principal, final String oldPass, final String newPass) {
        return this.userService.updatePassword(principal, oldPass, newPass);
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
