package com.ofg.event.service.concretes;

import com.ofg.event.model.entity.Role;
import com.ofg.event.model.entity.User;
import com.ofg.event.model.request.UserUpdateRequest;
import com.ofg.event.service.abstracts.RoleService;
import com.ofg.event.service.abstracts.UserRoleService;
import com.ofg.event.service.abstracts.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final RoleService roleService;
    private final UserService userService;

    public UserRoleServiceImpl(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void assignRoleToUser(long userId, String roleName) {
        Role role = roleService.getRoleByName(roleName);
        User user = userService.getUserEntityById(userId);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(user.getFirstName(), user.getLastName(),user.getPhoneNumber());
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userService.updateUser(user.getId(), userUpdateRequest, null);
        }
    }
}
