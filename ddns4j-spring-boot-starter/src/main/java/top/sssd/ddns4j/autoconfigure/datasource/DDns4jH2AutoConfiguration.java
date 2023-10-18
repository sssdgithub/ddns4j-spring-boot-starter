package top.sssd.ddns4j.autoconfigure.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author sssd
 * @create 2023-10-12-5:46
 */
@Configuration
@EnableConfigurationProperties(DDns4jDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Slf4j
public class DDns4jH2AutoConfiguration {

    @Bean("ddns4jDataSource")
    @ConditionalOnMissingBean
    public DataSource ddns4jDataSource(DDns4jDataSourceProperties ddns4jDataSourceProperties){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(ddns4jDataSourceProperties.getDriverClassName());
        dataSource.setJdbcUrl(ddns4jDataSourceProperties.getUrl());
        dataSource.setUsername(ddns4jDataSourceProperties.getUsername());
        dataSource.setPassword(ddns4jDataSourceProperties.getPassword());
        return dataSource;
    }


}
