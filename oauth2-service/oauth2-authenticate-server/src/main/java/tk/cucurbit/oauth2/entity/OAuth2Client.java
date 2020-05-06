package tk.cucurbit.oauth2.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@Table(name = "oauth2_client_details", schema = "public")
public class OAuth2Client implements ClientDetails {

    @Id
    private String id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name = "resource_ids")
    private String resourceIds;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "scopes")
    private String scopes;

    @Column(name = "redirect_uri")
    private String redirectUri;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "auto_approve")
    private String autoApprove;


    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        if (StringUtils.isBlank(resourceIds)) {
            return null;
        }
        return Arrays.stream(StringUtils.splitByWholeSeparator(resourceIds, ","))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isSecretRequired() {
        return StringUtils.isNotBlank(clientSecret);
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return StringUtils.isNotBlank(scopes)
                && StringUtils.splitByWholeSeparator(scopes, ",").length > 0;
    }

    @Override
    public Set<String> getScope() {
        if (StringUtils.isBlank(scopes)) {
            return Collections.emptySet();
        }
        return Arrays.stream(StringUtils.splitByWholeSeparator(scopes, ","))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if (StringUtils.isBlank(authorizedGrantTypes)) {
            return Collections.emptySet();
        }
        return Arrays.stream(StringUtils.splitByWholeSeparator(authorizedGrantTypes, ","))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if (StringUtils.isBlank(redirectUri)) {
            return Collections.emptySet();
        }
        return Arrays.stream(StringUtils.splitByWholeSeparator(redirectUri, ","))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (StringUtils.isBlank(authorities)) {
            return Collections.emptySet();
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (StringUtils.isBlank(autoApprove)) {
            return false;
        }
        return Arrays.stream(StringUtils.splitByWholeSeparator(autoApprove, ","))
                .anyMatch(auto -> StringUtils.equals(auto, "true"));
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        if (StringUtils.isBlank(additionalInformation)) {
            return new LinkedHashMap<>();
        }
        return JSON.parseObject(additionalInformation, new TypeReference<Map<String, Object>>() {
        });
    }

    public OAuth2Client(String id, String clientId, String clientSecret,
            Set<String> authorizedGrantTypes, Set<String> resourceIds,
            Set<String> authorities, Set<String> scopes, Set<String> redirectUri,
            Integer accessTokenValidity, Integer refreshTokenValidity,
            Map<String, Object> additionalInformation, Set<String> autoApprove
    ) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizedGrantTypes = String.join(
                ",", Optional.ofNullable(authorizedGrantTypes).orElse(Collections.emptySet())
        );
        this.resourceIds = String.join(
                ",", Optional.ofNullable(resourceIds).orElse(Collections.emptySet())
        );
        this.authorities = String.join(
                ",", Optional.ofNullable(authorities).orElse(Collections.emptySet())
        );
        this.scopes = String.join(
                ",", Optional.ofNullable(scopes).orElse(Collections.emptySet())
        );
        this.redirectUri = String.join(
                ",", Optional.ofNullable(redirectUri).orElse(Collections.emptySet())
        );
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.additionalInformation = JSON.toJSONString(additionalInformation);
        this.autoApprove = String.join(
                ",", Optional.ofNullable(autoApprove).orElse(Collections.emptySet())
        );
    }
}
