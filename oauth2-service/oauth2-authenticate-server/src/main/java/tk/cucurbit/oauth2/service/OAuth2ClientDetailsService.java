package tk.cucurbit.oauth2.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import tk.cucurbit.oauth2.cache.oauth2client.OAuth2ClientCache;
import tk.cucurbit.oauth2.dao.OAuth2ClientRepository;
import tk.cucurbit.oauth2.entity.OAuth2Client;

import java.util.Objects;

@Service
public class OAuth2ClientDetailsService implements ClientDetailsService {

    private final OAuth2ClientCache clientCache;
    private final OAuth2ClientRepository oAuth2ClientRepository;

    public OAuth2ClientDetailsService(OAuth2ClientRepository oAuth2ClientRepository, OAuth2ClientCache clientCache) {
        this.oAuth2ClientRepository = oAuth2ClientRepository;
        this.clientCache = clientCache;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if (StringUtils.isBlank(clientId)) {
            throw new ClientRegistrationException("client id cannot be null");
        }
        ClientDetails probability = clientCache.get(clientId);
        if (Objects.nonNull(probability)) {
            return probability;
        }
        OAuth2Client oauth2Client = oAuth2ClientRepository.findOneByClientId(clientId);
        if (Objects.isNull(oauth2Client)) {
            throw new ClientRegistrationException("Client Not Exist! Id: " + clientId);
        }
        clientCache.add(clientId, oauth2Client);
        return oauth2Client;
    }

}
