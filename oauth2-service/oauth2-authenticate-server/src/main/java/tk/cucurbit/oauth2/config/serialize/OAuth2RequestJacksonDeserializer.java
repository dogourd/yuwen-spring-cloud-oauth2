package tk.cucurbit.oauth2.config.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.io.IOException;

public class OAuth2RequestJacksonDeserializer extends StdDeserializer<OAuth2Request> {
    protected OAuth2RequestJacksonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OAuth2Request deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }


    @Override
    public OAuth2Request deserialize(JsonParser p, DeserializationContext ctxt, OAuth2Request intoValue) throws IOException {
        OAuth2Request oAuth2Request = null;

        ObjectMapper mapper = (ObjectMapper) p.getCodec();

        return oAuth2Request;
    }
}
