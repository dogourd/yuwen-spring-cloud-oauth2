package tk.cucurbit.oauth2.filters.oauth2;


import org.springframework.stereotype.Component;
import tk.cucurbit.oauth2.filters.AbstractPreZuulFilter;

/**
 * <h1> 审计日志 请求前 </h1>
 * */
@Component
public class AuditLogBeforeRequestZuulFilter extends AbstractPreZuulFilter {
    @Override
    protected Object cRun() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
