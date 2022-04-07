package com.purgerteam.log.trace.starter.instrument.reactive;

import com.purgerteam.log.trace.starter.TraceLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class TraceReactiveConfiguration {

    @Bean
    public TraceReactiveFilter traceReactiveFilter(TraceLogProperties traceLogProperties) {
        return new TraceReactiveFilter(traceLogProperties);
    }

}
