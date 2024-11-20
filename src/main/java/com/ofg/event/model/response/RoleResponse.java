package com.ofg.event.model.response;

import com.ofg.event.model.entity.Role;

public record RoleResponse(Long id, String name) {
    public RoleResponse(Role role) {
        this(role.getId(), role.getName());
    }
}
