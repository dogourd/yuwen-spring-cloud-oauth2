package tk.cucurbit.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.access.prepost.PreAuthorize;


@EnableFeignClients
@SpringCloudApplication
public class AuthenticateServerApplication {
@PreAuthorize(value = "hasAnyAuthority()")
    public static void main(String[] args) {
        SpringApplication.run(AuthenticateServerApplication.class, args);
    }

}
