package com.purgerteam.log.trace.starter.instrument.servlet;

import com.purgerteam.log.trace.starter.TraceContentFactory;
import com.purgerteam.log.trace.starter.TraceLogProperties;
import com.purgerteam.log.trace.starter.util.URLUtil;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求拦截器 初始化 Trace 内容
 *
 * @author <a href="mailto:yaoonlyi@gmail.com">purgeyao</a>
 * @since 1.0.0
 */
public class TraceServletFilter implements Filter {
    private final TraceLogProperties traceLogProperties;

    public TraceServletFilter(TraceLogProperties traceLogProperties) {
        this.traceLogProperties = traceLogProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        Map<String, String> formatMap = new HashMap<>(16);
        // 获取自定义参数
        Set<String> expandFormat = traceLogProperties.getFormat();
        for (String k : expandFormat) {
            String v = request.getHeader(k);
            if (StringUtils.hasText(v)) {
                formatMap.put(k, URLUtil.decode(v));
            }
        }

        // 写入 MDC
        TraceContentFactory.storageMDC(formatMap);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }

}
