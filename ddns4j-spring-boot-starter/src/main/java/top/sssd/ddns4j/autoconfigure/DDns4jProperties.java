package top.sssd.ddns4j.autoconfigure;

import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.bind.Name;

/**
 * @author sssd
 * @careate 2023-10-08-16:04
 */
@ConfigurationProperties(prefix = "ddns4j")
@Data
@ConditionalOnWebApplication
public class DDns4jProperties {
    /**
     * 是否开启ddns4j服务,默认为false,开启的话设置为true
     */
    private Boolean enabled = false;

    /**
     * boot工程正常关闭时,是否清除dns解析记录
     */
    private Boolean shutdownOnCleared = false;


    @NestedConfigurationProperty
    private EasyMode easyMode;

    /**
     * 简易模式,如需打开设置为true,打开后,
     * 只需要配置各厂商serviceProviderId和serviceProviderSecret及域名后即可解析
     */
    @ConstructorBinding
    @ConditionalOnProperty(prefix = "ddns4j", name = "easyMode", havingValue = "true")
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class EasyMode {

        /**
         * 服务提供商 1 阿里云 2 腾讯云 3 cloudflare不能为空
         */
        private Integer serviceProvider;
        /**
         * 服务商ID
         */
        private String serviceProviderId;
        /**
         * 服务商密钥
         */
        private String serviceProviderSecret;
        /**
         * 需要解析的域名
         */
        private String domain;

        public EasyMode(
                @Name("serviceProvider") Integer serviceProvider,
                @Name("serviceProviderId") @DefaultValue("") String serviceProviderId,
                @Name("serviceProviderSecret") @DefaultValue("") String serviceProviderSecret,
                @Name("domain") @DefaultValue("") String domain) {
            this.serviceProvider = serviceProvider;
            this.serviceProviderId = serviceProviderId;
            this.serviceProviderSecret = serviceProviderSecret;
            this.domain = domain;
        }
    }
}
