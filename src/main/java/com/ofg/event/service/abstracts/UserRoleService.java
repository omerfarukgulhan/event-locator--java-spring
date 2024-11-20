package com.ofg.event.service.abstracts;

public interface UserRoleService {
    void assignRoleToUser(long userId, String roleName);
}
