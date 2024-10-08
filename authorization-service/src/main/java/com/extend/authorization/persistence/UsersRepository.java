package com.extend.authorization.persistence;

import com.extend.authorization.model.Group;
import com.extend.authorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM Users u " +
            "JOIN FETCH u.groups g " +
            "JOIN FETCH g.roles r " +
            "JOIN FETCH r.permissions p " +
            "WHERE u.id = :userId")
    Optional<User> findUserWithGroupsAndRolesAndPermissions(String userId);
}
