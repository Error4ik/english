package com.voronin.english.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserController test class.
 *
 * @author Alexey Voronin.
 * @since 28.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class UserControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

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

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String actualValue = jsonParser.parseMap(result.getResponse().getContentAsString()).get("name").toString();
        assertThat(actualValue, is(expectedValue));
    }
}
