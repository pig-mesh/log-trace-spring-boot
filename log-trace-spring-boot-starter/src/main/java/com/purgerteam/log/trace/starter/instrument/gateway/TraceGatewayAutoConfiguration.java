package com.purgerteam.log.trace.starter.instrument.gateway;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关
 *
 * @author L.cm
 */
@Configuration
@ConditionalOnClass(GlobalFilter.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class TraceGatewayAutoConfiguration {

    @Bean
    public TraceGatewayFilter traceGatewayFilter() {
        return new TraceGatewayFilter();
    }

}
