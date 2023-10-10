package top.sssd.ddns4j.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sssd
 * @careate 2023-10-08-16:04
 */
@ConfigurationProperties(prefix = "ddns4j")
@Data
@ConditionalOnWebApplication
public class DDns4jProperties {
    private Boolean enabled = false;
}
