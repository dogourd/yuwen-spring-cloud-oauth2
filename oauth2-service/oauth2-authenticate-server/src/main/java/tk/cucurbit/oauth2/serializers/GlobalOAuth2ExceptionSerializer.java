package tk.cucurbit.oauth2.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tk.cucurbit.oauth2.exceptions.GlobalOAuth2Exception;

import java.io.IOException;

public class GlobalOAuth2ExceptionSerializer extends JsonSerializer<GlobalOAuth2Exception> {
    @Override
    public void serialize(GlobalOAuth2Exception value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("code", value.getErrorCode());
        gen.writeStringField("msg", value.getMsg());
        gen.writeStringField("data", null);

        gen.writeEndObject();
    }


}
