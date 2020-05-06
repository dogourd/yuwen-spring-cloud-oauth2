package tk.cucurbit.oauth2.service;

import org.springframework.stereotype.Service;
import tk.cucurbit.oauth2.dao.PermissionRepository;
import tk.cucurbit.oauth2.dao.RolePermissionRepository;
import tk.cucurbit.oauth2.dao.UserRoleRepository;
import tk.cucurbit.oauth2.entity.PermissionEntity;
import tk.cucurbit.oauth2.entity.RolePermissionEntity;
import tk.cucurbit.oauth2.entity.UserRoleEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    public PermissionService(UserRoleRepository userRoleRepository, RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository) {
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    public Set<String> findPermissionByUserId(String userId) {
        Set<String> roleIds = userRoleRepository.findAllByUserId(userId).stream()
                .map(UserRoleEntity::getRoleId).collect(Collectors.toSet());
        Set<String> permissionIds = rolePermissionRepository.findAllByRoleIdIn(roleIds).stream()
                .map(RolePermissionEntity::getPermissionId).collect(Collectors.toSet());
        return permissionRepository.findAllById(permissionIds).stream()
                .map(PermissionEntity::getCode).collect(Collectors.toSet());
    }
}
