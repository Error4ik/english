package com.voronin.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.voronin.auth.domain.User;
import com.voronin.auth.repository.UserRepository;
import com.voronin.auth.service.RoleService;
import com.voronin.auth.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * OauthController test class.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OauthController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
public class OauthControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock UserService.
     */
    @MockBean
    private UserService userService;

    /**
     * Mock TokenStore.
     */
    @MockBean
    private TokenStore tokenStore;

    /**
     * Mock HttpServletRequest.
     */
    @MockBean
    private HttpServletRequest request;

    /**
     * Mock OAuth2AccessToken.
     */
    @MockBean
    private OAuth2AccessToken accessToken;

    /**
     * Mock UserRepository.
     */
    @MockBean
    private UserRepository userRepository;

    /**
     * Mock RoleService.
     */
    @MockBean
    private RoleService roleService;

    /**
     * Java Mail Sender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * When Mapping '/registration' valid user should return status isOk
     * and return user and call regUser method once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingRegistrationShouldReturnStatusOkAndReturnUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user);

        when(this.userService.regUser(user)).thenReturn(Optional.of(user));

        this.mockMvc
                .perform(post("/registration")
                        .header("Content-type", "application/json; charset=utf-8").with(csrf())
                        .content(requestJson))
                .andExpect(status()
                        .isOk());

        verify(this.userService, times(1)).regUser(user);
    }

    /**
     * When Mapping '/registration' valid user should return status isOk
     * and return error and call regUser method once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingRegistrationShouldReturnStatusOkAndReturnWithError() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(user);
        Optional<User> result = Optional.empty();

        this.mockMvc
                .perform(post("/registration")
                        .header("Content-type", "application/json; charset=utf-8")
                        .with(csrf())
                        .content(requestJson))
                .andExpect(status()
                        .isOk())
                .andReturn();

        verify(this.userService, times(1)).regUser(user);
    }

    /**
     * When Mapping '/revoke' without token should return status isOk.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingRevokeAndRequestGetHeaderNullShouldReturnStatusOk() throws Exception {
        this.mockMvc
                .perform(post("/revoke")
                        .header("Content-type", "application/json; charset=utf-8")
                        .with(csrf()))
                .andExpect(status()
                        .isOk())
                .andReturn();
    }

    /**
     * When Mapping '/revoke' with token should return status isOk
     * and call removeAccessToken method of the TokenStore class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingRevokeAndRequestGetHeaderNotNullShouldReturnStatusOk() throws Exception {
        String authHeader = "test";
        when(this.tokenStore.readAccessToken(authHeader)).thenReturn(accessToken);
        this.mockMvc
                .perform(post("/revoke")
                        .header("Content-type", "application/json; charset=utf-8")
                        .header("Authorization", authHeader)
                        .with(csrf())
                        .flashAttr("request", request))
                .andExpect(status()
                        .isOk())
                .andReturn();

        verify(this.tokenStore, times(1)).removeAccessToken(accessToken);
    }

    /**
     * When Mapping '/activate/{key}' should return status isOk
     * and call activateUser method of the UserService class once
     * and return String with flag true.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingActivateWithKeyShouldReturnStatusOkAndCallActivateUserMethodOnce() throws Exception {
        final UUID uuid = UUID.randomUUID();
        when(this.userService.activateUser(uuid.toString())).thenReturn(new User());
        this.mockMvc
                .perform(get("/activate/{key}", uuid.toString()))
                .andExpect(status().isOk());

        verify(this.userService, times(1)).activateUser(uuid.toString());
    }

    /**
     * @throws Exception exception.
     */
    @Test
    public void whenMappingUsersShouldReturnListOfUser() throws Exception {
        this.mockMvc.perform(get("/users")).andExpect(status().isOk());

        verify(this.userService, times(1)).getUsers();
    }

    /**
     * When mapping '/user/current' should return mock user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingUserCurrentShouldReturnMockUser() throws Exception {
        String expectedValue = "user";

        MvcResult result = mockMvc.perform(get("/user/current"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result);
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String actualValue = jsonParser.parseMap(result.getResponse().getContentAsString()).get("name").toString();
        assertThat(actualValue, is(expectedValue));
    }

    /**
     * When mapping /roles should return status isOk
     * and call method getRoles by RoleService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingRolesShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/roles")).andExpect(status().isOk());

        verify(this.roleService, times(1)).getRoles();
    }

    /**
     * When mapping /change-role should return status isOk
     * and call method changeUserRole by UserService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingChangeRoleShouldReturnStatusOk() throws Exception {
        final String userId = UUID.randomUUID().toString();
        final String roleId = UUID.randomUUID().toString();
        this.mockMvc.perform(get("/change-role")
                .param("userId", userId)
                .param("roleId", roleId)
        ).andExpect(status().isOk());

        verify(this.userService, times(1))
                .changeUserRole(UUID.fromString(userId), UUID.fromString(roleId));
    }

    /**
     * When mapping "/userId" should return status isOk and
     * call method getUserIdByEmail by UserService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingUserIdShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/user")).andExpect(status().isOk());

        verify(this.userService, times(1)).getUserByEmail(any(String.class));
    }
}
