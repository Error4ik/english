package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RoleService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void whenFindRoleByNameShouldReturnRole() throws Exception {
        Role role = new Role();
        role.setRole("user");
        when(roleRepository.findRoleByRole(role.getRole())).thenReturn(role);

        assertThat(roleService.findRoleByName(role.getRole()), is(role));
    }
}
