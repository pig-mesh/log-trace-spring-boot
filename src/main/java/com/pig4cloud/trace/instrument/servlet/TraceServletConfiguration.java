package com.pig4cloud.trace.instrument.servlet;

import com.pig4cloud.trace.TraceLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class TraceServletConfiguration {

	@Bean
	public TraceServletFilter traceServletFilter(TraceLogProperties traceLogProperties) {
		return new TraceServletFilter(traceLogProperties);
	}

}
