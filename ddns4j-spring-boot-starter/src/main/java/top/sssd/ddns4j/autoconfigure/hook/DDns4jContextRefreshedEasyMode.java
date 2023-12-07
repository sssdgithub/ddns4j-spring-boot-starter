package top.sssd.ddns4j.autoconfigure.hook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.sssd.ddns.common.enums.RecordTypeEnum;
import top.sssd.ddns.common.enums.UpdateFrequencyEnum;
import top.sssd.ddns.model.entity.ParsingRecord;
import top.sssd.ddns.model.response.NetWorkSelectResponse;
import top.sssd.ddns.service.IParsingRecordService;
import top.sssd.ddns4j.autoconfigure.DDns4jProperties;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author sssd
 * @careate 2023-10-25-13:35
 */
@Slf4j
@Component
public class DDns4jContextRefreshedEasyMode implements ApplicationRunner {

    @Resource
    private IParsingRecordService parsingRecordService;
    @Resource
    private DDns4jProperties dDns4jProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(Objects.isNull(dDns4jProperties)
                || Boolean.FALSE.equals(dDns4jProperties.getEnabled())
                || Objects.isNull(dDns4jProperties.getEasyMode())){
            return;
        }
        ParsingRecord parsingRecord = new ParsingRecord();
        parsingRecord.setState(1);
        parsingRecord.setRecordType(RecordTypeEnum.IPV6.getIndex());
        parsingRecord.setGetIpMode(1);
        parsingRecord.setServiceProviderId(dDns4jProperties.getEasyMode().getServiceProviderId());
        parsingRecord.setServiceProviderSecret(dDns4jProperties.getEasyMode().getServiceProviderSecret());
        parsingRecord.setServiceProvider(dDns4jProperties.getEasyMode().getServiceProvider());
        parsingRecord.setUpdateFrequency(UpdateFrequencyEnum.ONE_MINUTE.getCode());
        String domain = dDns4jProperties.getEasyMode().getDomain();

        List<NetWorkSelectResponse> modeIpValue = parsingRecordService.getModeIpValue(parsingRecord.getGetIpMode(), parsingRecord.getRecordType());
        String getModeIpValue = modeIpValue.stream().findAny().get().getValue().trim();
        parsingRecord.setGetIpModeValue(getModeIpValue);

        parsingRecord.setDomain(domain);

        List<ParsingRecord> list = parsingRecordService.lambdaQuery()
                .eq(ParsingRecord::getDomain, dDns4jProperties.getEasyMode().getDomain())
                .eq(ParsingRecord::getState,1)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            try {
                parsingRecordService.add(parsingRecord);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("ddns4j已正常启动,您填入的域名为:{}",domain);
            return;
        }
        try {
            parsingRecordService.modify(list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("ddns4j已正常启动,您填入的域名为:{}",domain);
    }
}
