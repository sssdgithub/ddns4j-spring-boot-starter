package top.sssd.ddns4j.autoconfigure;

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
    /**
     * 是否开启ddns4j服务,默认为false,像开启的话设置为true
     */
    private Boolean enabled = false;

    /**
     * boot工程正常关闭时,是否清除dns解析记录
     */
    private Boolean shutdownOnCleared = false;
}
