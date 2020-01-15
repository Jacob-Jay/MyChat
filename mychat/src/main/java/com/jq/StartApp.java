package com.jq;

import com.jq.spring.RootConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jiangqing
 * @version 1.0
 * @since 2020-01-15 16:21
 */
public class StartApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
    }

}
