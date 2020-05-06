package tk.cucurbit.oauth2.filters.oauth2;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import tk.cucurbit.oauth2.entity.UserInfo;
import tk.cucurbit.oauth2.filters.AbstractPreZuulFilter;

/**
 * <h1> 认证过滤器 </h1>
 * */
@Component
public class AuthenticateZuulFilter extends AbstractPreZuulFilter {

    @Override
    protected Object cRun() {
        // todo can access db or cache
        boolean needAuthenticate = RandomUtils.nextInt(1, 10) % 2 == 0;
        if (needAuthenticate) {
            String token = readToken(requestContext);
            UserInfo userInfo = getUserInfoByToken(token);
        }
        return null;
    }

    private UserInfo getUserInfoByToken(String token) {
        return null;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    private String readToken(RequestContext context) {
        String tokenValue = context.getRequest().getHeader("Authorization");
        if (StringUtils.isBlank(tokenValue)) {
            return StringUtils.EMPTY;
        }
        if (!StringUtils.startsWith(tokenValue, "Bearer ")) {
            return StringUtils.EMPTY;
        }
        return StringUtils.substringAfter(tokenValue, "Bearer ");
    }


}
