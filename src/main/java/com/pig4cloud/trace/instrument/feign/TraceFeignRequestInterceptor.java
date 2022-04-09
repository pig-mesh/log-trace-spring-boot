package com.pig4cloud.trace.instrument.feign;

import com.pig4cloud.trace.TraceContentFactory;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Map;

/**
 * @author purgeyao
 * @since 1.0
 */
public class TraceFeignRequestInterceptor implements RequestInterceptor {

	private final TraceContentFactory traceContentFactory;

	public TraceFeignRequestInterceptor(TraceContentFactory traceContentFactory) {
		this.traceContentFactory = traceContentFactory;
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {
		Map<String, String> copyOfContextMap = traceContentFactory.assemblyTraceContent();
		for (Map.Entry<String, String> copyOfContext : copyOfContextMap.entrySet()) {
			requestTemplate.header(copyOfContext.getKey(), copyOfContext.getValue());
		}
	}

}
