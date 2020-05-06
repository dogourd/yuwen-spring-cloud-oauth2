package tk.cucurbit.oauth2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.cucurbit.oauth2.entity.OAuth2Client;

public interface OAuth2ClientRepository extends JpaRepository<OAuth2Client, String> {

    OAuth2Client findOneByClientId(String clientId);
}
