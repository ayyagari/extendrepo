package com.extend.authorization.service;

import com.extend.authorization.model.Group;
import com.extend.authorization.model.Permission;
import com.extend.authorization.model.Role;
import com.extend.authorization.model.User;
import com.extend.authorization.persistence.GroupsRepository;
import com.extend.authorization.persistence.PermissionsRepository;
import com.extend.authorization.persistence.RolesRepository;
import com.extend.authorization.persistence.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    final UsersRepository usersRepository;
    final GroupsRepository groupsRepository;
    final RolesRepository rolesRepository;
    final PermissionsRepository permissionsRepository;

    public AuthorizationServiceImpl(UsersRepository usersRepository, GroupsRepository groupsRepository, RolesRepository rolesRepository, PermissionsRepository permissionsRepository) {
        this.usersRepository = usersRepository;
        this.groupsRepository = groupsRepository;
        this.rolesRepository = rolesRepository;
        this.permissionsRepository = permissionsRepository;
    }

    @Override
    public boolean isUserAuthorized(String userId, String uri, String method) {
        //TODO: URI sub-paths and methods linking to permission should be managed in the database instead of code.
        if (uri.endsWith("/accounts")) {
            if (method.equalsIgnoreCase("GET"))
                return isUserAuthorizedForPermission(userId, "VIEW_ACCOUNT");
            else if (method.equalsIgnoreCase("POST")
                    || method.equalsIgnoreCase("PUT")
            ) {
                return isUserAuthorizedForPermission(userId, "CREATE_ACCOUNT");
            }
        }
        return false;

    }
    private boolean isUserAuthorizedForPermission(String userId, String permissionName) {
        if (!usersRepository.existsById(userId)) {
            return false;
        }
        User user = usersRepository.findUserWithGroupsAndRolesAndPermissions(userId).get();

        for (Group group : user.getGroups()) {
            for (Role role : group.getRoles()) {
                for (Permission p : role.getPermissions()) {
                    // Check if the permission name matches the desired name
                    if (p.getName().equalsIgnoreCase(permissionName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
