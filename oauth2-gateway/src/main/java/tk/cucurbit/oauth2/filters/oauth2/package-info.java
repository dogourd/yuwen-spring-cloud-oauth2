package tk.cucurbit.oauth2.filters.oauth2;

/**
 * oauth2 过滤器
 *
 * 0. 流控                SimpleRateLimitZuulFilter
 * 1. 认证                AuthenticateZuulFilter
 * 2. 前置审计日志         AuditLogBeforeRequestZuulFilter
 * 3. 授权                AuthorizationZuulFilter
 * 4. 后置审计日志         AuditLogAfterRequestZuulFilter
 *
 * */