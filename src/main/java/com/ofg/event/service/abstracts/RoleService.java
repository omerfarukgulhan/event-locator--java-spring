package com.ofg.event.service.abstracts;

import com.ofg.event.model.entity.Role;
import com.ofg.event.model.request.RoleCreateRequest;
import com.ofg.event.model.request.RoleUpdateRequest;
import com.ofg.event.model.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleResponse> getAllRoles(Pageable pageable);

    RoleResponse getRoleById(long roleId);

    Role getRoleByName(String name);

    RoleResponse addRole(RoleCreateRequest roleCreateRequest);

    RoleResponse updateRole(long roleId, RoleUpdateRequest roleUpdateRequest);

    void deleteRole(long roleId);
}
