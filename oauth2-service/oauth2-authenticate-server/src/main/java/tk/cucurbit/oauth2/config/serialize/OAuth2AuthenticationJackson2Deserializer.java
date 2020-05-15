package tk.cucurbit.oauth2.config.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.IOException;

public class OAuth2AuthenticationJackson2Deserializer extends StdDeserializer<OAuth2Authentication> {
    protected OAuth2AuthenticationJackson2Deserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OAuth2Authentication deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
        return objectMapper.readValue(jsonParser, OAuth2Authentication.class);
    }
}
