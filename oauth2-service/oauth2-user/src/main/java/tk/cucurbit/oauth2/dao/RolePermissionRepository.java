package tk.cucurbit.oauth2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.cucurbit.oauth2.entity.RolePermissionEntity;

import java.util.List;
import java.util.Set;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, String> {

    List<RolePermissionEntity> findAllByRoleIdIn(Set<String> roleIds);
}
