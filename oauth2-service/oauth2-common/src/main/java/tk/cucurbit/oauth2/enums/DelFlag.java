package tk.cucurbit.oauth2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  DelFlag {

    NORMAL(0, "正常"),
    DELETED(1, "已删除"),
    ;

    private int code;
    private String description;
}
