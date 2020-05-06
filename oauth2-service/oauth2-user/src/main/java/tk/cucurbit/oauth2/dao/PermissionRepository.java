package tk.cucurbit.oauth2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.cucurbit.oauth2.entity.PermissionEntity;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<PermissionEntity, String> {

    List<PermissionEntity> findAllByIdIn(Set<String> ids);
}
