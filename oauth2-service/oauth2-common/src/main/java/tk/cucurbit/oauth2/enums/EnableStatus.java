package tk.cucurbit.oauth2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableStatus {

    ENABLE(0, "启用状态"),
    DISABLE(1, "禁用状态"),
    ;

    private int code;
    private String description;
}
