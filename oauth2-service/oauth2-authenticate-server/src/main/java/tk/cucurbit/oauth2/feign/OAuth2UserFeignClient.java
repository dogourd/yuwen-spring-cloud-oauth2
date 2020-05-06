package tk.cucurbit.oauth2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tk.cucurbit.oauth2.entity.UserEntity;
import tk.cucurbit.oauth2.vo.CommonResponse;

import java.util.Set;

@FeignClient(name = "eureka-client-oauth2-user")
public interface OAuth2UserFeignClient {

    @GetMapping("/oauth2-user/username/{username}")
    CommonResponse<UserEntity> findUserByUsername(@PathVariable(name = "username") String username);

    @GetMapping("/oauth2-user/permission/getByUserId")
    CommonResponse<Set<String>> findPermissionsByUserId(@RequestParam(name = "userId") String userId);
}
