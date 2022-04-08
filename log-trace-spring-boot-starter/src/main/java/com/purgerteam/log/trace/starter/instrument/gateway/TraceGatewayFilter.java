package com.purgerteam.log.trace.starter.instrument.gateway;

import com.purgerteam.log.trace.starter.Constants;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 响应 TraceId 处理器
 *
 * @author L.cm
 */
public class TraceGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        // 1. 获取 traceId
        String traceId = MDC.get(Constants.LEGACY_TRACE_ID_NAME);
        // 2. 处理响应的 header traceId
        HttpHeaders responseHeaders = response.getHeaders();
        if (traceId != null) {
            responseHeaders.set(Constants.LEGACY_TRACE_ID_NAME, traceId);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}