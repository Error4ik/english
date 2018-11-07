package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleByName(final String name) {
        return this.roleRepository.findRoleByRole(name);
    }
}
