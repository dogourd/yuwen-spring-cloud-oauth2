package tk.cucurbit.oauth2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {


    OAUTH2_SUCCESS(200000, "成功"),

    OAUTH2_UNAUTHORIZED(400001, "未认证"),
    OAUTH2_FORBIDDEN(400003, "禁止访问"),
    OAUTH2_NOTFOUND(400004, "资源未找到"),
    OAUTH2_METHOD_NOT_ALLOW(400005, "请求方式不支持"),
    OAUTH2_USER_NOT_FOUND(400006, "用户未找到"),

    OAUTH2_SERVER_ERROR(500000, "内部异常"),
    ;

    private int code;
    private String msg;
}
