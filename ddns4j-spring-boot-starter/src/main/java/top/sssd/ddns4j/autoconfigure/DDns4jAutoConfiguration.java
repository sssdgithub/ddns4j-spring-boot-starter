package top.sssd.ddns4j.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;
import top.sssd.ddns.common.enums.RecordTypeEnum;
import top.sssd.ddns.common.enums.UpdateFrequencyEnum;
import top.sssd.ddns.model.entity.ParsingRecord;
import top.sssd.ddns.service.IParsingRecordService;
import top.sssd.ddns4j.autoconfigure.datasource.DDns4jH2DataSourceInitializer;
import top.sssd.ddns4j.autoconfigure.hook.DDns4jClearDnsRecordShutDownHook;
import top.sssd.ddns4j.autoconfigure.service.EasyModeCondition;

import javax.sql.DataSource;
import java.util.List;


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

    @Autowired
    private IParsingRecordService parsingRecordService;
    @Autowired
    private DDns4jProperties dDns4jProperties;

    @EventListener
    @Conditional(EasyModeCondition.class)
    public void handleContextRefreshedEasyMode(ContextRefreshedEvent event) throws Exception {
        ParsingRecord parsingRecord = new ParsingRecord();
        parsingRecord.setState(1);
        parsingRecord.setRecordType(RecordTypeEnum.IPV6.getIndex());
        parsingRecord.setGetIpMode(1);
        parsingRecord.setServiceProviderId(dDns4jProperties.getEasyMode().getServiceProviderId());
        parsingRecord.setServiceProviderSecret(dDns4jProperties.getEasyMode().getServiceProviderSecret());
        parsingRecord.setServiceProvider(dDns4jProperties.getEasyMode().getServiceProvider());
        parsingRecord.setUpdateFrequency(UpdateFrequencyEnum.ONE_MINUTE.getCode());
        parsingRecord.setDomain(dDns4jProperties.getEasyMode().getDomain());


        List<ParsingRecord> list = parsingRecordService.lambdaQuery()
                .eq(ParsingRecord::getDomain, dDns4jProperties.getEasyMode().getDomain())
                .eq(ParsingRecord::getState,1)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            parsingRecordService.add(parsingRecord);
            return;
        }
        parsingRecordService.modify(list.get(0));
    }




}
