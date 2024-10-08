-- ALTER TABLE User_Groups DROP CONSTRAINT fk_ug_users;
-- ALTER TABLE User_Groups DROP CONSTRAINT fk_ug_groups;
-- DROP TABLE IF EXISTS User_Groups;
--
-- ALTER TABLE Group_Roles DROP CONSTRAINT fk_gr_roles;
-- ALTER TABLE Group_Roles DROP CONSTRAINT fk_gr_groups;
-- DROP TABLE IF EXISTS Group_Roles;
--
-- ALTER TABLE Role_Permissions DROP CONSTRAINT fk_rp_roles;
-- ALTER TABLE Role_Permissions DROP CONSTRAINT fk_rp_permissions;
-- DROP TABLE IF EXISTS Role_Permissions;

DROP TABLE IF EXISTS Users;
CREATE TABLE Users
(
    id VARCHAR(36) PRIMARY KEY
);

DROP TABLE IF EXISTS Groups;
CREATE TABLE Groups
(
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles
(
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS Permissions;
CREATE TABLE Permissions
(
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE User_Groups
(
    user_id  VARCHAR(36) NOT NULL,
    group_id VARCHAR(36) NOT NULL,
    -- Foreign key to the users table
    CONSTRAINT fk_ug_users
        FOREIGN KEY (user_id) REFERENCES Users (id)
            ON DELETE CASCADE,

    -- Foreign key to the groups table
    CONSTRAINT fk_ug_groups
        FOREIGN KEY (group_id) REFERENCES groups (id)
            ON DELETE CASCADE,

    -- Ensure that each combination of user and group is unique
    UNIQUE (user_id, group_id)
);


CREATE TABLE Group_Roles
(
    role_id  VARCHAR(36) NOT NULL,
    group_id VARCHAR(36) NOT NULL,
    -- Foreign key to the users table
    CONSTRAINT fk_gr_roles
        FOREIGN KEY (role_id) REFERENCES Roles (id)
            ON DELETE CASCADE,

    -- Foreign key to the groups table
    CONSTRAINT fk_gr_groups
        FOREIGN KEY (group_id) REFERENCES Groups (id)
            ON DELETE CASCADE,

    -- Ensure that each combination of user and group is unique
    UNIQUE (role_id, group_id)
);


CREATE TABLE Role_Permissions
(
    role_id  VARCHAR(36) NOT NULL,
    permission_id VARCHAR(36) NOT NULL,
    -- Foreign key to the users table
    CONSTRAINT fk_rp_roles
        FOREIGN KEY (role_id) REFERENCES Roles (id)
            ON DELETE CASCADE,

    -- Foreign key to the groups table
    CONSTRAINT fk_rp_permissions
        FOREIGN KEY (permission_id) REFERENCES Permissions (id)
            ON DELETE CASCADE,

    -- Ensure that each combination of user and group is unique
    UNIQUE (role_id, permission_id)
);