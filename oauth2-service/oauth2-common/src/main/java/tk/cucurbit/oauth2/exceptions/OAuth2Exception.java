package tk.cucurbit.oauth2.exceptions;

import lombok.Getter;

@Getter
public class OAuth2Exception extends Exception {

    private int httpStatus;
    private int errorCode;
    private String msg;

    public OAuth2Exception(int httpStatus, int errorCode, String message) {
        super(message);
        this.msg = message;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
