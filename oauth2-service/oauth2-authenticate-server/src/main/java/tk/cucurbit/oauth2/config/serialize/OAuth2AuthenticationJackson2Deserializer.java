package tk.cucurbit.oauth2.config.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OAuth2AuthenticationJackson2Deserializer extends JsonDeserializer<OAuth2Authentication> {

    @Override
    public OAuth2Authentication deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        OAuth2Authentication oAuth2Authentication = null;

        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode jsonNode = mapper.readTree(p);
        JsonNode oauth2RequestNode = readJsonNode(jsonNode, "storedRequest");
        JsonNode userAuthenticationNode = readJsonNode(jsonNode, "userAuthentication");
        JsonNode detailsNode = readJsonNode(jsonNode, "details");

        OAuth2Request oAuth2Request = null;
        if (oauth2RequestNode.isObject()) {
            oAuth2Request = parseOAuth2Request(oauth2RequestNode, mapper);
        }

        Authentication authentication = null;
        if (userAuthenticationNode.isObject()) {
            authentication = mapper.readValue(userAuthenticationNode.traverse(mapper), UsernamePasswordAuthenticationToken.class);
        }
        oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        if (detailsNode.isObject()) {
            oAuth2Authentication.setDetails(mapper.readValue(detailsNode.traverse(mapper), OAuth2AuthenticationDetails.class));
        }

        return oAuth2Authentication;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

    private OAuth2Request parseOAuth2Request(JsonNode json, ObjectMapper mapper) throws IOException {

        Map<String, String> requestParameters = mapper.readValue(json.get("requestParameters").traverse(mapper), new TypeReference<Map<String, String>>() {
        });
        String clientId = json.get("clientId").asText();
        String grantType = json.get("grantType").asText();
        String redirectUri = json.get("redirectUri").asText();
        boolean approved = json.get("approved").asBoolean();
        Set<String> responseTypes = mapper.readValue(json.get("responseTypes").traverse(mapper), new TypeReference<Set<String>>() {
        });
        Set<String> scope = mapper.readValue(json.get("scope").traverse(mapper), new TypeReference<Set<String>>() {
        });
        Set<String> authorities = mapper.readValue(json.get("authorities").traverse(mapper), new TypeReference<Set<String>>() {
        });
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(0);
        if (authorities != null && !authorities.isEmpty()) {
            authorities.forEach(s -> grantedAuthorities.add(new SimpleGrantedAuthority(s)));
        }
        Set<String> resourceIds = mapper.readValue(json.get("resourceIds").traverse(mapper), new TypeReference<Set<String>>() {
        });
        Map<String, Serializable> extensions = mapper.readValue(json.get("extensions").traverse(mapper), new TypeReference<Map<String, Serializable>>() {
        });

        OAuth2Request request = new OAuth2Request(requestParameters, clientId,
                grantedAuthorities, approved, scope, resourceIds, redirectUri, responseTypes, extensions);
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientId, scope, grantType);
        request.refresh(tokenRequest);
        return request;
    }

}
