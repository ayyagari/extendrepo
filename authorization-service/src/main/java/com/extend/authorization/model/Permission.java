package com.extend.authorization.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    private String id;

    private String name;  // e.g., "CREATE_ACCOUNT", "VIEW_ACCOUNT", etc.
}
