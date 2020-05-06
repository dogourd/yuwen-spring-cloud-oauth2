package tk.cucurbit.oauth2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private int code;

    private String msg;

    private T data;

    public CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
