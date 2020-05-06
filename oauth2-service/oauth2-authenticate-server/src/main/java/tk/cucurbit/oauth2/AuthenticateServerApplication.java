package tk.cucurbit.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringCloudApplication
public class AuthenticateServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticateServerApplication.class, args);
    }

}
