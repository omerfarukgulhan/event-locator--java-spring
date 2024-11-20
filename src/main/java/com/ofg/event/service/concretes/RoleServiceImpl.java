package com.ofg.event.service.concretes;

import com.ofg.event.exception.general.NotFoundException;
import com.ofg.event.model.entity.Role;
import com.ofg.event.model.request.RoleCreateRequest;
import com.ofg.event.model.request.RoleUpdateRequest;
import com.ofg.event.model.response.RoleResponse;
import com.ofg.event.repository.RoleRepository;
import com.ofg.event.service.abstracts.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(RoleResponse::new);
    }

    @Override
    public RoleResponse getRoleById(long roleId) {
        return roleRepository.findById(roleId)
                .map(RoleResponse::new)
                .orElseThrow(() -> new NotFoundException(roleId));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(name));
    }

    @Override
    public RoleResponse addRole(RoleCreateRequest roleCreateRequest) {
        Role role = new Role();
        role.setName(roleCreateRequest.name());
        Role savedRole = roleRepository.save(role);
        return new RoleResponse(savedRole);
    }

    @Override
    public RoleResponse updateRole(long roleId, RoleUpdateRequest roleUpdateRequest) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId));

        role.setName(roleUpdateRequest.name());
        Role updatedRole = roleRepository.save(role);
        return new RoleResponse(updatedRole);
    }

    @Override
    public void deleteRole(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId));
        roleRepository.delete(role);
    }
}
