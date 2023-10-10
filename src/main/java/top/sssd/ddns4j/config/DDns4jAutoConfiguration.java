package top.sssd.ddns4j.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sssd
 * @careate 2023-10-08-16:08
 */
@EnableConfigurationProperties(DDns4jProperties.class)
@ConditionalOnProperty(name = "ddns4j.enabled", havingValue = "true")
@ComponentScan(basePackages = {"top.sssd.ddns"})
@MapperScan("top.sssd.ddns.mapper")
public class DDns4jAutoConfiguration {

}
