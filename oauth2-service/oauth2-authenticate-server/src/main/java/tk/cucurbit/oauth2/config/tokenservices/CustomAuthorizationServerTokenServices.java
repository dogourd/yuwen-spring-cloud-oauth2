//package tk.cucurbit.oauth2.config.tokenservices;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2RefreshToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.TokenRequest;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.UUID;
//
//@Component
//public class CustomAuthorizationServerTokenServices implements AuthorizationServerTokenServices {
//
//    private final TokenStore redisTokenStore;
//
//    public CustomAuthorizationServerTokenServices(TokenStore redisTokenStore) {
//        this.redisTokenStore = redisTokenStore;
//    }
//
//    @Override
//    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
//        OAuth2AccessToken existingAccessToken = redisTokenStore.getAccessToken(authentication);
//        OAuth2RefreshToken refreshToken = null;
//        if (existingAccessToken != null) {
//            if (existingAccessToken.isExpired()) {
//                if (existingAccessToken.getRefreshToken() != null) {
//                    refreshToken = existingAccessToken.getRefreshToken();
//                    // The token store could remove the refresh token when the
//                    // access token is removed, but we want to
//                    // be sure...
//                    redisTokenStore.removeRefreshToken(refreshToken);
//                }
//                redisTokenStore.removeAccessToken(existingAccessToken);
//            }
//            else {
//                // Re-store the access token in case the authentication has changed
//                redisTokenStore.storeAccessToken(existingAccessToken, authentication);
//                return existingAccessToken;
//            }
//        }
//
//        // Only create a new refresh token if there wasn't an existing one
//        // associated with an expired access token.
//        // Clients might be holding existing refresh tokens, so we re-use it in
//        // the case that the old access token
//        // expired.
//        if (refreshToken == null) {
//            refreshToken = createRefreshToken(authentication);
//        }
//        // But the refresh token itself might need to be re-issued if it has
//        // expired.
//        else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
//            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
//            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
//                refreshToken = createRefreshToken(authentication);
//            }
//        }
//
//        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
//        redisTokenStore.storeAccessToken(accessToken, authentication);
//        // In case it was modified
//        refreshToken = accessToken.getRefreshToken();
//        if (refreshToken != null) {
//            redisTokenStore.storeRefreshToken(refreshToken, authentication);
//        }
//        return accessToken;
//    }
//
//    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
//        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
//        int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
//        if (validitySeconds > 0) {
//            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
//        }
//        token.setRefreshToken(refreshToken);
//        token.setScope(authentication.getOAuth2Request().getScope());
//
//        return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
//    }
//
//    @Override
//    public OAuth2AccessToken refreshAccessToken(String refreshToken, TokenRequest tokenRequest) throws AuthenticationException {
//        return null;
//    }
//
//    @Override
//    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
//        return null;
//    }
//}
