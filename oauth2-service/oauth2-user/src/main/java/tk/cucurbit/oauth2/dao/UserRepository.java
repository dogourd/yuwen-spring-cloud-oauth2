package tk.cucurbit.oauth2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.cucurbit.oauth2.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findOneByUsername(String username);
}
