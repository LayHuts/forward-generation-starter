package lyj.forward.generation.ddlhandler;

import lyj.forward.generation.entity.ConfigureEntityTable;
import lyj.forward.generation.entity.entityInfo.EntityMetaInfo;
import lyj.forward.generation.enums.DdlType;
import lyj.forward.generation.excutor.DdlExecutor;
import lyj.forward.generation.excutor.Excutor;
import lyj.forward.generation.properties.LyjMySqlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/8 15:00
 */
public class DataBaseServiceHandler extends DdlBaseService
{
    private static Logger logger = LoggerFactory.getLogger(DataBaseServiceHandler.class);

    private Excutor excutor;
    private ConfigureEntityTable configureEntityTable;

    public DataBaseServiceHandler(DdlExecutor excutor, ConfigureEntityTable configureEntityTable)
    {
        this.excutor = excutor;
        this.configureEntityTable = configureEntityTable;
    }

    @Override
    Excutor getExcutor()
    {
        return this.excutor;
    }

    @Override
    public void excute()
    {
        logger.info("开始更新数据库.....");
        // 库里的表名
        List<String> tableNames = configureEntityTable.getTableNames();

        // 实体类
        Map<String, EntityMetaInfo> entityMetaInfoMap = configureEntityTable.getEntityMetaInfoMap();

        try
        {
            // 判断类型
            LyjMySqlProperties properties = this.configureEntityTable.getProperties();
            if (DdlType.CREATE.name().equals(properties.getDdlAuto().toUpperCase()))
            {
                dropTable(tableNames);
                createTable(entityMetaInfoMap);
            }
            else
            {
                createTable(tableNames, configureEntityTable, entityMetaInfoMap);
            }
            excutor.commit();
            logger.info("更新完成.....");
        }
        catch (SQLException e)
        {
            try
            {
                excutor.rollback();
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            e.printStackTrace();
            logger.error("对不起，出现了错误");
        }
    }
}
