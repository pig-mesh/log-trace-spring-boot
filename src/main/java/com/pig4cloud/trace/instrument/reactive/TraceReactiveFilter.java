package com.pig4cloud.trace.instrument.reactive;

import com.pig4cloud.trace.TraceContentFactory;
import com.pig4cloud.trace.TraceLogProperties;
import com.pig4cloud.trace.util.URLUtil;
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
		return chain.filter(exchange).doFinally(t -> MDC.clear());
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

}
