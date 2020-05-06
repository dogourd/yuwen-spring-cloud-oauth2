package tk.cucurbit.oauth2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.cucurbit.oauth2.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {

    List<UserRoleEntity> findAllByUserId(String userId);
}
