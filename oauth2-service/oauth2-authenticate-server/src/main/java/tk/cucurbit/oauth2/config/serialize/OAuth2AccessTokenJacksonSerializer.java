package tk.cucurbit.oauth2.config.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OAuth2AccessTokenJacksonSerializer extends StdSerializer<OAuth2AccessToken> {

    public OAuth2AccessTokenJacksonSerializer() {
        this(OAuth2AccessToken.class);
    }

    public OAuth2AccessTokenJacksonSerializer(Class<OAuth2AccessToken> t) {
        super(t);
    }

    @Override
    public void serializeWithType(OAuth2AccessToken value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, serializers);
    }


    @Override
    public void serialize(OAuth2AccessToken accessToken, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField(OAuth2AccessToken.ACCESS_TOKEN, accessToken.getValue());
        gen.writeStringField(OAuth2AccessToken.TOKEN_TYPE, accessToken.getTokenType());
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            gen.writeStringField(OAuth2AccessToken.REFRESH_TOKEN, refreshToken.getValue());
        }
        Date deadline = accessToken.getExpiration();
        if (Objects.nonNull(deadline)) {
            long now = System.currentTimeMillis();
            gen.writeNumberField(OAuth2AccessToken.EXPIRES_IN, (deadline.getTime() - now) / 1000);
        }
        Set<String> scopes = accessToken.getScope();
        if (CollectionUtils.isNotEmpty(scopes)) {
            String scope = String.join(" ", scopes);
            gen.writeStringField(OAuth2AccessToken.SCOPE, scope);
        }
        Map<String, Object> information = accessToken.getAdditionalInformation();
        for (Map.Entry<String, Object> entry : information.entrySet()) {
            gen.writeObjectField(entry.getKey(), entry.getValue());
        }

        gen.writeEndObject();
    }
}
