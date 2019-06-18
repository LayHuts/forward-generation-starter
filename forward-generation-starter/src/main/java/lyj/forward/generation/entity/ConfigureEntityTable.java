package lyj.forward.generation.entity;

import lyj.forward.generation.annotation.LTable;
import lyj.forward.generation.database.table.TableMetaInfo;
import lyj.forward.generation.entity.entityInfo.EntityMetaInfo;
import lyj.forward.generation.exception.MyException;
import lyj.forward.generation.properties.LyjMySqlProperties;
import lyj.forward.generation.utils.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 11:53
 */
public class ConfigureEntityTable
{

    /**
     * <br>
     * 表前缀
     */
    private static final String TABLLE_PREFIX="tb_";

    /**
     * <br>
     * 配置信息
     */
    private LyjMySqlProperties properties;


    /**
     * <br>
     * 实体表数据
     */
    private Map<String,EntityMetaInfo> entityMetaInfoMap;

    private Map<String, TableMetaInfo> tableMetaInfoMap;

    private List<String> tableNames;
    private List<String> entityTableNames;


    public EntityMetaInfo getEntityInfo(String tableName){
        EntityMetaInfo metaInfo = entityMetaInfoMap.get(tableName);
        if (metaInfo==null){
            throw new MyException("取不到名为 "+tableName+" 的实体表对象那！");
        }
        return metaInfo;
    }

    public TableMetaInfo getTabledInfo(String tableName){
        TableMetaInfo metaInfo = tableMetaInfoMap.get(tableName);
        if (metaInfo==null){
            throw new MyException("取不到名为 "+tableName+" 的实体表对象那！");
        }
        return metaInfo;
    }


    public Map<String, EntityMetaInfo> getEntityMetaInfoMap()
    {
        return entityMetaInfoMap;
    }

    public void setEntityMetaInfoMap(Map<String, EntityMetaInfo> entityMetaInfoMap)
    {
        this.entityMetaInfoMap = entityMetaInfoMap;
    }

    public static String produceTableName(LTable annotation,Class<?> aClass){
        String name = annotation.name();
        if ("".equals(name)) {
            // 将实体转换为下划线
            name = TABLLE_PREFIX + StringUtil.uncapitalizeToUnderLine(aClass.getSimpleName());
        }
        return name;
    }

    public Map<String, TableMetaInfo> getTableMetaInfoMap()
    {
        return tableMetaInfoMap;
    }

    public void setTableMetaInfoMap(Map<String, TableMetaInfo> tableMetaInfoMap)
    {
        this.tableMetaInfoMap = tableMetaInfoMap;
    }

    public List<String> getTableNames()
    {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames)
    {
        this.tableNames = tableNames;
    }

    public List<String> getEntityTableNames()
    {
        return entityTableNames;
    }

    public void setEntityTableNames(List<String> entityTableNames)
    {
        this.entityTableNames = entityTableNames;
    }

    public LyjMySqlProperties getProperties()
    {
        return properties;
    }

    public void setProperties(LyjMySqlProperties properties)
    {
        this.properties = properties;
    }
}
