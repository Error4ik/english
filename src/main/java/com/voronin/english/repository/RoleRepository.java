package com.voronin.english.repository;

import com.voronin.english.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Role repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Get role bu name.
     *
     * @param name name.
     * @return role.
     */
    Role findRoleByRole(String name);

    /**
     * Get Role by id.
     *
     * @param uuid role id.
     * @return Role.
     */
    Role getRoleById(UUID uuid);
}
