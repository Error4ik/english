package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.repository.RoleRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * RoleService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class RoleServiceTest {
    /**
     * Mock RoleRepository.
     */
    private RoleRepository roleRepository = mock(RoleRepository.class);

    /**
     * The class object under test.
     */
    private RoleService roleService = new RoleService(roleRepository);

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
        verify(roleRepository, times(1)).findRoleByRole(role.getRole());
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
        verify(roleRepository, times(1)).findAll();
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
        verify(roleRepository, times(1)).getRoleById(uuid);
    }
}
