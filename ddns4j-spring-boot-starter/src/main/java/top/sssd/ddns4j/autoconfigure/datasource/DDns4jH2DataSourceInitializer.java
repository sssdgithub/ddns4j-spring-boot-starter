package top.sssd.ddns4j.autoconfigure.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author sssd
 * @careate 2023-10-16-23:03
 */
@Component
@Slf4j
public class DDns4jH2DataSourceInitializer extends DataSourceInitializer {

    private DataSource dataSource;

    private final static String SCRIPT_SQL_TABLE_FIRST = "job_task";
    private final static String SCRIPT_SQL_PATH = "sql/ddns4j_h2.sql";
    private final static String EXIST_TABLE_SQL = "SELECT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?)";

    public DDns4jH2DataSourceInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Boolean aBoolean = jdbcTemplate.queryForObject(EXIST_TABLE_SQL, Boolean.class, SCRIPT_SQL_TABLE_FIRST);
        if(!aBoolean){
            log.info("ddns4j sql starting...");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource(SCRIPT_SQL_PATH));
            DatabasePopulatorUtils.execute(populator, dataSource);
            log.info("ddns4j sql ending...");
        }
    }
}
