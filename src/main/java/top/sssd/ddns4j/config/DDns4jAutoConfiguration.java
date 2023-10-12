package top.sssd.ddns4j.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author sssd
 * @create 2023-10-08-16:08
 */
@Configuration
@EnableConfigurationProperties(value = {DDns4jProperties.class})
@ConditionalOnProperty(name = "ddns4j.enabled", havingValue = "true")
@ComponentScan(basePackages = {"top.sssd.ddns"})
@MapperScan("top.sssd.ddns.mapper")
@AutoConfigureBefore(QuartzAutoConfiguration.class)
public class DDns4jAutoConfiguration {

    @Resource
    @Qualifier("ddns4jDataSource")
    private DataSource dataSource;

    @Bean
    @ConditionalOnMissingBean
    public SchedulerFactoryBean scheduler() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        return schedulerFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new SpringBeanJobFactory();
    }

}
