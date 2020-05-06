package tk.cucurbit.oauth2.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public abstract class AbstractZuulFilter extends ZuulFilter {

    protected RequestContext requestContext;
    private static final String SHOULD_FILTER_KEY = "next";

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();

        return ctx.getBoolean(SHOULD_FILTER_KEY, true);
    }

    @Override
    public Object run() throws ZuulException {
        requestContext = RequestContext.getCurrentContext();
        return cRun();
    }


    @SuppressWarnings("deprecation")
    protected Object fail(int code, String msg) {
        requestContext.set(SHOULD_FILTER_KEY, false);
        requestContext.setSendZuulResponse(false);
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        requestContext.getResponse().setStatus(HttpStatus.OK.value());
        // code msg data
        requestContext.setResponseBody(String.format("{\"code\": %d, \"msg\": %s, \"data\": null}", code, msg));
        return null;
    }

    protected Object success() {
        requestContext.set(SHOULD_FILTER_KEY, true);
        return null;
    }

    protected abstract Object cRun();
}
