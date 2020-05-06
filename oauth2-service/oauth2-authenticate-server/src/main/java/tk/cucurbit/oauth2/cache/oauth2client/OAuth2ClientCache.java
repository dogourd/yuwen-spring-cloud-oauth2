package tk.cucurbit.oauth2.cache.oauth2client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;
import tk.cucurbit.oauth2.cache.CacheAware;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
public class OAuth2ClientCache implements CacheAware<String, ClientDetails> {

    private static final ConcurrentMap<String, ClientDetails> CACHE = new ConcurrentHashMap<>(4);


    @Override
    public ClientDetails add(String clientId, ClientDetails clientDetails) {
        return CACHE.put(clientId, clientDetails);
    }

    @Override
    public ClientDetails get(String clientId) {
        log.info("OAuth2ClientCache, Get From Cache, ClientId Is: {}", clientId);
        return CACHE.get(clientId);
    }

    @Override
    public void update(String clientId, ClientDetails clientDetails) {
        CACHE.put(clientId, clientDetails);
    }

    @Override
    public void delete(String clientId, ClientDetails clientDetails) {
        CACHE.remove(clientId, clientDetails);
    }

    @Override
    public void clear(String clientId) {
        CACHE.remove(clientId);
    }
}
