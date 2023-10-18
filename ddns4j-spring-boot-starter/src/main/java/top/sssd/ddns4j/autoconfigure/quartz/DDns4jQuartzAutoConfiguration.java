package top.sssd.ddns4j.autoconfigure.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author sssd
 * @create 2023-10-16-22:09
 */
@Configuration
@AutoConfigureBefore({QuartzAutoConfiguration.class})
public class DDns4jQuartzAutoConfiguration {

    @Resource
    @Qualifier("ddns4jDataSource")
    private DataSource dataSource;

    @Resource
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    public SchedulerFactoryBean scheduler() throws SchedulerException {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setApplicationContext(applicationContext);
        return schedulerFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringBeanJobFactory springBeanJobFactory() throws SchedulerException {
        SpringBeanJobFactory springBeanJobFactory = new SpringBeanJobFactory();
        return springBeanJobFactory;
    }
}
