package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Role service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class RoleService {

    /**
     * Role repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Constructor.
     *
     * @param roleRepository role repository.
     */
    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Find role by name.
     *
     * @param name role name.
     * @return Role.
     */
    public Role findRoleByName(final String name) {
        return this.roleRepository.findRoleByRole(name);
    }

    /**
     * Return all roles.
     *
     * @return list of Role.
     */
    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }

    /**
     * Get Role by id.
     *
     * @param roleId role id.
     * @return Role.
     */
    public Role getRoleById(final UUID roleId) {
        return this.roleRepository.getRoleById(roleId);
    }
}
