package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
