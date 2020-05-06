package tk.cucurbit.oauth2.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import tk.cucurbit.oauth2.serializers.GlobalOAuth2ExceptionSerializer;

@Getter
@JsonSerialize(using = GlobalOAuth2ExceptionSerializer.class)
public class GlobalOAuth2Exception extends OAuth2Exception {


    private int errorCode;

    private String msg;

    public GlobalOAuth2Exception(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
