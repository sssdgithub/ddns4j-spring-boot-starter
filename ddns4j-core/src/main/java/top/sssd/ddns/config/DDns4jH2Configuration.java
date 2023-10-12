package top.sssd.ddns.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author sssd
 * @create 2023-10-12-5:46
 */
@Configuration
@EnableConfigurationProperties(DDns4jDataSourceProperties.class)
public class DDns4jH2Configuration {

    @Bean("ddns4jDataSource")
    @ConditionalOnMissingBean(name = "ddns4jDataSource")
    public DataSource ddns4jDataSource(DDns4jDataSourceProperties ddns4jDataSourceProperties){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(ddns4jDataSourceProperties.getDriverClassName());
        hikariDataSource.setJdbcUrl(ddns4jDataSourceProperties.getUrl());
        hikariDataSource.setUsername(ddns4jDataSourceProperties.getUsername());
        hikariDataSource.setPassword(ddns4jDataSourceProperties.getPassword());
        return hikariDataSource;
    }

    @Bean("ddns4jJdbcTemplate")
    @ConditionalOnMissingBean
    public JdbcTemplate jdbcTemplate(@Qualifier("ddns4jDataSource") DataSource ddns4jDataSource){
        return new JdbcTemplate(ddns4jDataSource);
    }
}
