package main.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义一个全局过滤器
 * 需要实现:GlobalFilter, Ordered
 */
@Component
public class LogonFilter implements GlobalFilter, Ordered {
    /**
     * 重写filter方法,执行过滤器中的业务逻辑
     * 对请求参数中的token进行判断
     * 如果存在:代表人证成功
     * 如果不存在:代表认证失败
     * ServerWebExchange:相当于请求和响应上下文(等同于zuul中的RequestContext)
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("执行了自定义全局过滤器中的方法");
        String url = exchange.getRequest().getURI().getPath();
        // 忽略以下url请求
        if (url.indexOf("/login") >= 0) {
            return chain.filter(exchange);
        }
//       1. 获取请求参数
        String token = exchange.getRequest().getQueryParams().getFirst("token");
//        2.判断token是否存在
        if (StringUtils.isBlank(token)) {
            System.out.println("token is empty ...");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);//继续向下执行
    }

    /**
     * 执行过滤器的顺序,返回值越小,优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
