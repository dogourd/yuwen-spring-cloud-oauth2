package tk.cucurbit.oauth2.filters.oauth2;

import org.springframework.stereotype.Component;
import tk.cucurbit.oauth2.filters.AbstractPostZuulFilter;

/**
 * <h1> 审计日志 请求完成后 </h1>
 * */
@Component
public class AuditLogAfterRequestZuulFilter extends AbstractPostZuulFilter {
    @Override
    protected Object cRun() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
