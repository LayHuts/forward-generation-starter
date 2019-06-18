package lyj.forward.generation;

import lyj.forward.generation.annotation.LTable;
import lyj.forward.generation.database.TableParserHandler;
import lyj.forward.generation.ddlhandler.DataBaseServiceHandler;
import lyj.forward.generation.entity.ConfigureEntityTable;
import lyj.forward.generation.entity.EntityParserHandler;
import lyj.forward.generation.excutor.DdlExecutor;
import lyj.forward.generation.jdbc.LJdbc;
import lyj.forward.generation.properties.LyjMySqlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/7 14:27
 */
//@Configuration
@EnableConfigurationProperties(LyjMySqlProperties.class)
public class LyjAutoConfigure implements InitializingBean
{
    private static Logger logger = LoggerFactory.getLogger(LyjAutoConfigure.class);
    /**
     * 注入 配置文件
     */
    private LyjMySqlProperties mySqlProperties;

    private ConfigurableListableBeanFactory beanFactory;

    private DdlExecutor ddlExecutor;
    private LJdbc lJdbc;

    public LyjAutoConfigure(LyjMySqlProperties mySqlProperties, ConfigurableListableBeanFactory beanFactory)
    {
        this.mySqlProperties = mySqlProperties;
        this.beanFactory = beanFactory;
        this.lJdbc =new LJdbc(this.mySqlProperties);
        this.ddlExecutor =new DdlExecutor(lJdbc);
    }

    @Override
    public void afterPropertiesSet()
    {
        // 获取自定义类注解的类
        List<Class<?>> classes = new ArrayList<>();
        Map<String, Object> map = beanFactory.getBeansWithAnnotation(LTable.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            classes.add(entry.getValue().getClass());
        }

        if (classes.size()<=0){
            return;
        }

        ConfigureEntityTable configureEntity = new ConfigureEntityTable();
        configureEntity.setProperties(this.mySqlProperties);

        // 解析实体
        EntityParserHandler parserHandler = new EntityParserHandler(configureEntity);
        parserHandler.parser(classes);

        // 读取数据库中的表信息
        TableParserHandler tableParserHandler = new TableParserHandler(configureEntity,ddlExecutor);
        tableParserHandler.parser();

        DataBaseServiceHandler serviceHandler = new DataBaseServiceHandler(this.ddlExecutor,configureEntity);
        serviceHandler.excute();

        logger.info("表已更新-------->");

    }
}
