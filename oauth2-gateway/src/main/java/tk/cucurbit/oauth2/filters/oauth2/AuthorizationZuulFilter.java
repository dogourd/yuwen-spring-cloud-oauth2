package tk.cucurbit.oauth2.filters.oauth2;

import org.springframework.stereotype.Component;
import tk.cucurbit.oauth2.filters.AbstractPreZuulFilter;

/**
 * <h1> 授权过滤器 </h1>
 * */
@Component
public class AuthorizationZuulFilter extends AbstractPreZuulFilter {
    @Override
    protected Object cRun() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
