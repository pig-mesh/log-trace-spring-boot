package com.pig4cloud.trace.instrument.zuul;

import com.netflix.zuul.ZuulFilter;
import com.pig4cloud.trace.TraceContentFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@ConditionalOnClass({ ZuulFilter.class, TraceContentFactory.class })
public class TraceZuulAutoConfiguration {

	@Bean
	public TracePreZuulFilter traceZuulFilter(TraceContentFactory traceContentFactory) {
		return new TracePreZuulFilter(traceContentFactory);
	}

}
