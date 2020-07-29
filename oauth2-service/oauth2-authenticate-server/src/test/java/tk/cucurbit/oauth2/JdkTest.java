package tk.cucurbit.oauth2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import tk.cucurbit.oauth2.entity.OAuth2User;

@RunWith(BlockJUnit4ClassRunner.class)
public class JdkTest {
    @Test
    public void test() {
        OAuth2User user = new OAuth2User();
        user.setUsername("admin");
        JdkSerializationStrategy strategy = new JdkSerializationStrategy();
        byte[] bytes = strategy.serialize(user);
        user = strategy.deserialize(bytes, OAuth2User.class);
        System.out.println(user.getUsername());


    }
}
