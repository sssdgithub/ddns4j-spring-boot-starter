package top.sssd.ddns4j.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.sssd.ddns4j.autoconfigure.datasource.DDns4jH2DataSourceInitializer;
import top.sssd.ddns4j.autoconfigure.hook.DDns4jClearDnsRecordShutDownHook;
import top.sssd.ddns4j.autoconfigure.hook.DDns4jContextRefreshedEasyMode;

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
@Slf4j
public class DDns4jAutoConfiguration  {

    @Bean
    @ConditionalOnProperty(name = "ddns4j.shutdown-on-cleared", havingValue = "true")
    public DisposableBean ClearDnsRecordHook() {
        return new DDns4jClearDnsRecordShutDownHook();
    }

    @Bean
    public DDns4jH2DataSourceInitializer ddns4jH2DataSourceInitializer(DataSource dataSource) {
        DDns4jH2DataSourceInitializer dataSourceInitializer = new DDns4jH2DataSourceInitializer(dataSource);
        return dataSourceInitializer;
    }

    @Bean
    public DDns4jContextRefreshedEasyMode handleContextRefreshedEasyMode() {
        return new DDns4jContextRefreshedEasyMode();
    }




}
