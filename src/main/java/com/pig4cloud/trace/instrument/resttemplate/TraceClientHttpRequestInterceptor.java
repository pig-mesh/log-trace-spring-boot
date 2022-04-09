package com.pig4cloud.trace.instrument.resttemplate;

import com.pig4cloud.trace.TraceContentFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @author purgeyao
 * @since 1.0
 */
public class TraceClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	private final TraceContentFactory traceContentFactory;

	public TraceClientHttpRequestInterceptor(TraceContentFactory traceContentFactory) {
		this.traceContentFactory = traceContentFactory;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
			ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
		HttpHeaders headers = httpRequest.getHeaders();
		Map<String, String> copyOfContextMap = traceContentFactory.assemblyTraceContent();
		for (Map.Entry<String, String> copyOfContext : copyOfContextMap.entrySet()) {
			headers.add(copyOfContext.getKey(), copyOfContext.getValue());
		}
		return clientHttpRequestExecution.execute(httpRequest, bytes);
	}

}
