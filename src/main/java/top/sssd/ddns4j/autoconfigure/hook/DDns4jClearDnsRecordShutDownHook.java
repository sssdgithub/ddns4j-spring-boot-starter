package top.sssd.ddns4j.autoconfigure.hook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.sssd.ddns.model.entity.JobTask;
import top.sssd.ddns.model.entity.ParsingRecord;
import top.sssd.ddns.service.IJobTaskService;
import top.sssd.ddns.service.IParsingRecordService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sssd
 * @careate 2023-10-13-22:56
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "ddns4j.shutdown-on-cleared", havingValue = "true")
public class DDns4jClearDnsRecordShutDownHook implements DisposableBean {

    @Resource
    IParsingRecordService parsingRecordService;

    @Resource
    IJobTaskService jobTaskService;


    //todo 持久化功能消失,简易版开关,如果加上 只需要配置属性可以,不需要显示页面
    @Override
    public void destroy() throws Exception {
        log.info("DDns4J ClearDnsRecordHook Destroy Starting...");
        List<JobTask> jobTaskList = jobTaskService.list();
        if (CollectionUtils.isEmpty(jobTaskList)) {
            log.info("DDns4J ClearDnsRecordHook Destroy Completed.");
            return;
        }
        for (JobTask jobTask : jobTaskList) {
            jobTaskService.stopJobTask(jobTask.getId());
            jobTaskService.deleteJobTask(jobTask.getId());
        }
        List<ParsingRecord> list = parsingRecordService.list();
        if (CollectionUtils.isEmpty(list)) {
            log.info("DDns4J ClearDnsRecordHook Destroy Completed.");
            return;
        }
        for (ParsingRecord parsingRecord : list) {
            parsingRecordService.delete(parsingRecord.getId());
        }
        log.info("DDns4J ClearDnsRecordHook Destroy Completed.");
    }
}
