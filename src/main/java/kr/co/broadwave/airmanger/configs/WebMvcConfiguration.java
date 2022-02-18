package kr.co.broadwave.airmanger.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Remark :
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    private static final String CLASSPATH_RESOURCE_LOCATIONS ="classpath:/static/";

    @Override // 본Config 파일을 추가하면 Pageable 관련 생성자 에러가나는부분을 해결하기위함
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).setCachePeriod(31536000);
    }

}
