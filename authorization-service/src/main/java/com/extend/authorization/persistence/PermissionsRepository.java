package com.extend.authorization.persistence;

import com.extend.authorization.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Permission, String> {
}
