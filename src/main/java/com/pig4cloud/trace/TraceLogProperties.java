package com.pig4cloud.trace;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * 链路追踪配置
 *
 * @author <a href="mailto:yaoonlyi@gmail.com">purgeyao</a>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.trace.log")
public class TraceLogProperties {

	/**
	 * 日志格式顺序
	 */
	private Set<String> format = new HashSet<>();

	public Set<String> getFormat() {
		return format;
	}

	public void setFormat(Set<String> format) {
		this.format = format;
	}

}
