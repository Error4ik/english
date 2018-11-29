package com.voronin.english.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.voronin.english.domain.User;
import com.voronin.english.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OauthController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class OauthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenStore tokenStore;
    @MockBean
    private HttpServletRequest request;
    @MockBean
    private OAuth2AccessToken accessToken;

    @Test
    public void whenMappingRegistrationShouldReturnStatusOkAndReturnWithoutError() throws Exception {
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
                        .header("Content-type", "application/json; charset=utf-8")
                        .with(csrf())
                        .content(requestJson))
                .andExpect(status()
                        .isOk())
                .andReturn();

        verify(this.userService, times(1)).regUser(user);
    }

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

        when(this.userService.regUser(user)).thenReturn((result));

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

        verify(this.tokenStore, timeout(1)).removeAccessToken(accessToken);
    }
}
