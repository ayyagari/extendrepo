package com.extend.authorization.persistence;

import com.extend.authorization.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, String> {
}
