package top.sssd.ddns.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sssd
 * @careate 2023-10-08-16:04
 */
@ConfigurationProperties(prefix = "ddns4j.datasource")
@Data
@ConditionalOnWebApplication
public class DDns4jDataSourceProperties {
    private String url="jdbc:h2:file:./ddns4j";
    private String username="ddns4j";
    private String password="ddns4j";
    private String driverClassName="org.h2.Driver";
}
