package com.runmag.web.repository;

import com.runmag.web.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
