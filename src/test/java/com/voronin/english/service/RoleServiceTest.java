package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.repository.RoleRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * RoleService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RoleService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class RoleServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private RoleService roleService;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock RoleRepository.
     */
    @MockBean
    private RoleRepository roleRepository;

    /**
     * When findRoleByName should return role.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenFindRoleByNameShouldReturnRole() throws Exception {
        Role role = new Role();
        role.setRole("user");
        when(roleRepository.findRoleByRole(role.getRole())).thenReturn(role);

        assertThat(roleService.findRoleByName(role.getRole()), is(role));
    }

    /**
     * When getRoles should return list of Role.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetRolesShouldReturnListOfRole() throws Exception {
        List<Role> roles = Lists.newArrayList(new Role("admin"), new Role("user"));
        when(roleRepository.findAll()).thenReturn(roles);

        assertThat(this.roleService.getRoles(), is(roles));
    }

    /**
     * When getRoleById should return Role.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetRoleByIdShouldReturnRole() throws Exception {
        Role role = new Role("user");
        UUID uuid = UUID.randomUUID();
        when(roleRepository.getRoleById(uuid)).thenReturn(role);

        assertThat(this.roleService.getRoleById(uuid), is(role));
    }
}
