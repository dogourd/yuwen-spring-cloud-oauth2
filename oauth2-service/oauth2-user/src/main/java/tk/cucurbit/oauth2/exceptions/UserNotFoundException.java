package tk.cucurbit.oauth2.exceptions;

import org.springframework.http.HttpStatus;
import tk.cucurbit.oauth2.enums.ResponseCode;

public class UserNotFoundException extends OAuth2Exception {

    public UserNotFoundException(String message) {
        super(HttpStatus.OK.value(), ResponseCode.OAUTH2_NOTFOUND.getCode(), message);
    }
}
