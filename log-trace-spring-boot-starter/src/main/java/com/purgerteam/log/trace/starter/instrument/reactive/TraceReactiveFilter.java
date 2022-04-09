package com.purgerteam.log.trace.starter.instrument.reactive;

import com.purgerteam.log.trace.starter.TraceContentFactory;
import com.purgerteam.log.trace.starter.TraceLogProperties;
import com.purgerteam.log.trace.starter.util.URLUtil;
import org.slf4j.MDC;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * spring webflux 或者 spring cloud gateway 使用
 *
 * @author L.cm
 */
public class TraceReactiveFilter implements OrderedWebFilter {
    private final TraceLogProperties traceLogProperties;

    public TraceReactiveFilter(TraceLogProperties traceLogProperties) {
        this.traceLogProperties = traceLogProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        Map<String, String> formatMap = new HashMap<>(16);
        // 获取自定义参数
        Set<String> expandFormat = traceLogProperties.getFormat();
        for (String k : expandFormat) {
            String v = headers.getFirst(k);
            if (StringUtils.hasText(v)) {
                formatMap.put(k, URLUtil.decode(v));
            }
        }

        // 写入 MDC
        TraceContentFactory.storageMDC(formatMap);

        // 传递 新的header
        Map<String, String> copyOfContextMap = TraceContentFactory.assemblyTraceContentStatic();
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.headers(httpHeaders -> {
            for (Map.Entry<String, String> copyOfContext : copyOfContextMap.entrySet()) {
                httpHeaders.set(copyOfContext.getKey(), copyOfContext.getValue());
            }
        });

        return chain.filter(exchange.mutate().request(mutate.build()).build())
                .doFinally(t -> MDC.clear());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
