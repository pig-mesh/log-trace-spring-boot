package com.pig4cloud.trace.instrument.dubbo;

import com.pig4cloud.trace.TraceContentFactory;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({ DubboAutoConfiguration.class, TraceContentFactory.class })
public class TraceDubboConfiguration {

}
