package com.jinke.common.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FilterWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截

        registry.addInterceptor(new LoggerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/vendor/**","/ajax/**","/css/**","/extra/**","/js/**","ruoyi/**","/img/**","/i18n/**","/fonts/**","/file/**","/ruoyi/**","/error");
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}