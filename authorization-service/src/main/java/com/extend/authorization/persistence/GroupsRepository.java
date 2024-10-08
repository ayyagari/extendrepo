package com.extend.authorization.persistence;

import com.extend.authorization.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Group, String> {
}
