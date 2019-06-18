package lyj.forward.generation.entity.entityInfo;

import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 11:54
 */
public class EntityMetaInfo
{
    /**
     * <br>
     * 实体名字
     */
    private String name;

    /**
     * <br>
     * 指定的表名
     */
    private String tableName;

    /**
     * <br>
     * 属性字段
     */
    private List<EntityFieldMetaInfo> fieldInfos;

    /**
     * <br>
     * 列名
     */
    private List<String> columNames;


    public EntityMetaInfo()
    {
    }

    public EntityMetaInfo(String name, String tableName, List<EntityFieldMetaInfo> fieldInfos, List<String> columNames)
    {
        this.name = name;
        this.tableName = tableName;
        this.fieldInfos = fieldInfos;
        this.columNames = columNames;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public List<EntityFieldMetaInfo> getFieldInfos()
    {
        return fieldInfos;
    }

    public void setFieldInfos(List<EntityFieldMetaInfo> fieldInfos)
    {
        this.fieldInfos = fieldInfos;
    }

    public List<String> getColumNames()
    {
        return columNames;
    }

    public void setColumNames(List<String> columNames)
    {
        this.columNames = columNames;
    }
}
