package com.pig4cloud.trace.instrument.resttemplate;

import com.pig4cloud.trace.TraceContentFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ RestTemplate.class, TraceContentFactory.class })
public class TraceRestTemplateConfiguration {

	@Bean
	public RestTemplatePostProcessor restTemplatePostProcessor(TraceContentFactory traceContentFactory) {
		return new RestTemplatePostProcessor(traceContentFactory);
	}

}
