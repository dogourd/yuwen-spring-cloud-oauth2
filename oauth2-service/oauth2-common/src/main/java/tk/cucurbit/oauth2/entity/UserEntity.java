package tk.cucurbit.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private String id;

    private String username;

    private String nickName;

    private String password;

    private String phone;

    private Integer gender;

    private String headImgUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer delFlag;

    private Integer type;

    private Integer enabled;

}
