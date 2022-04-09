package com.pig4cloud.trace;

import com.pig4cloud.trace.handlers.DefaultTraceMetaObjectHandler;
import com.pig4cloud.trace.handlers.TraceMetaObjectHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(TraceLogProperties.class)
public class TraceAutoConfiguration {

	@Bean
	public TraceContentFactory traceContentFactory(Map<String, TraceMetaObjectHandler> traceMetaObjectHandlerMap) {
		return new TraceContentFactory(traceMetaObjectHandlerMap);
	}

	@Bean
	public DefaultTraceMetaObjectHandler defaultTraceMetaObjectHandler() {
		return new DefaultTraceMetaObjectHandler();
	}

}
