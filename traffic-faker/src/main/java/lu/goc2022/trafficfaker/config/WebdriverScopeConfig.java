package lu.goc2022.trafficfaker.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebdriverScopeConfig {
    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor(){
        return new WebdriverScopePostProcessor();
    }
}