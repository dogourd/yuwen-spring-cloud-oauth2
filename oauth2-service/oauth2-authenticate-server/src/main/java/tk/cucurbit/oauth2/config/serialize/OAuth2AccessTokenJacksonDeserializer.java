package tk.cucurbit.oauth2.config.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.google.common.collect.Sets;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

import java.io.IOException;
import java.util.*;

public class OAuth2AccessTokenJacksonDeserializer extends StdDeserializer<OAuth2AccessToken> {

    public OAuth2AccessTokenJacksonDeserializer() {
        this(OAuth2AccessToken.class);
    }

    public OAuth2AccessTokenJacksonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return des(p, ctxt, typeDeserializer);
    }

    private DefaultOAuth2AccessToken des(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return deserialize(p, ctxt);
    }

    @Override
    public DefaultOAuth2AccessToken deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();

        Map<String, Object> information = new LinkedHashMap<>();
        JsonNode jsonNode = mapper.readTree(p);

        String accessToken = null;
        JsonNode accessTokenNode = readJsonNode(jsonNode, OAuth2AccessToken.ACCESS_TOKEN);
        if (!accessTokenNode.isNull()) {
            accessToken = accessTokenNode.asText();
        }

        String tokenType = null;
        JsonNode tokenTypeNode = readJsonNode(jsonNode, OAuth2AccessToken.TOKEN_TYPE);
        if (!tokenTypeNode.isNull()) {
            tokenType = tokenTypeNode.asText();
        }

        String refreshToken = null;
        JsonNode refreshTokenNode = readJsonNode(jsonNode, OAuth2AccessToken.REFRESH_TOKEN);
        if (!refreshTokenNode.isNull()) {
            refreshToken = refreshTokenNode.asText();
        }

        Long expiresIn = null;
        JsonNode expiresInNode = readJsonNode(jsonNode, OAuth2AccessToken.EXPIRES_IN);
        if (!expiresInNode.isNull()) {
            expiresIn = expiresInNode.asLong();
        }

        Set<String> scopes = null;
        JsonNode scopesNode = readJsonNode(jsonNode, OAuth2AccessToken.SCOPE);
        if (scopesNode.isArray()) {
            scopes = new LinkedHashSet<>();
            Iterator<JsonNode> iterator = scopesNode.iterator();
            while (iterator.hasNext()) {
                scopes.add(iterator.next().asText());
            }
        } else {
            scopes = OAuth2Utils.parseParameterList(scopesNode.asText());
        }
        Set<String> names = Sets.newHashSet(OAuth2AccessToken.ACCESS_TOKEN,
                OAuth2AccessToken.TOKEN_TYPE, OAuth2AccessToken.REFRESH_TOKEN,
                OAuth2AccessToken.EXPIRES_IN, OAuth2AccessToken.SCOPE);
        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            if (!names.contains(fieldName)) {
                information.put(fieldName, mapper.readValue(readJsonNode(jsonNode, fieldName).traverse(mapper), Object.class));
            }
        }

        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        oAuth2AccessToken.setTokenType(tokenType);
        if (Objects.nonNull(expiresIn)) {
            oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + expiresIn * 1000));
        }
        if (Objects.nonNull(refreshToken)) {
            oAuth2AccessToken.setRefreshToken(new DefaultOAuth2RefreshToken(refreshToken));
        }
        oAuth2AccessToken.setScope(scopes);
        oAuth2AccessToken.setAdditionalInformation(information);

        return oAuth2AccessToken;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
