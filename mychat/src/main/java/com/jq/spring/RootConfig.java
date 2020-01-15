package com.jq.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-15 16:28
 */
@Configuration
@ComponentScan(basePackages = {"com.jq.netty","com.jq.spring"})
public class RootConfig {
}
