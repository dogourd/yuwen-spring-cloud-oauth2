package tk.cucurbit.oauth2.cache.oauth2client;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class OAuth2ClientCacheObject {

    private String id;

    private String clientId;

    private String clientSecret;

    private Set<String> authorizedGrantTypes;

    private Set<String> resourceIds;

    private Set<String> authorities;

    private Set<String> scopes;

    private Set<String> redirectUri;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private Map<String, Object> additionalInformation;

    private Set<String> autoApprove;


    public OAuth2ClientCacheObject(String id, String clientId, String clientSecret,
                                   Set<String> authorizedGrantTypes, Set<String> resourceIds,
                                   Set<String> authorities, Set<String> scopes, Set<String> redirectUri,
                                   Integer accessTokenValidity, Integer refreshTokenValidity,
                                   Map<String, Object> additionalInformation, Set<String> autoApprove
    ) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.resourceIds = resourceIds;
        this.authorities = authorities;
        this.scopes = scopes;
        this.redirectUri = redirectUri;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.additionalInformation = additionalInformation;
        this.autoApprove = autoApprove;
    }
}
