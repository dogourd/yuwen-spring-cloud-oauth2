package tk.cucurbit.oauth2.filters.oauth2;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tk.cucurbit.oauth2.filters.AbstractPreZuulFilter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <h1> 简单流控(网关层面) guava 实现, 单个网关流量控制 </h1>
 * */
@Component
@SuppressWarnings("all")
public class SimpleRateLimitZuulFilter extends AbstractPreZuulFilter {

    private static final RateLimiter rateLimiter = RateLimiter.create(10, 10, TimeUnit.SECONDS);

    @Override
    protected Object cRun() {
        boolean canAccess = rateLimiter.tryAcquire(Duration.ofMillis(3L));
        if (canAccess) {
            return fail(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
        }
        return success();
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
