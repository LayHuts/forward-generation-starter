package lyj.forward.generation.database.table;

import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 14:02
 */
public class TableMetaInfo
{
    /**
     * <br>
     * 表名
     */
    private String tableName;


    /**
     * <br>
     * 列名
     */
    private List<String> columNames;

    /**
     * <br>
     * 当前表的所有列
     */
    private List<TableFieldMetaInfo> metaInfos;

    public TableMetaInfo()
    {
    }

    public TableMetaInfo(String tableName, List<String> columNames, List<TableFieldMetaInfo> metaInfos)
    {
        this.tableName = tableName;
        this.columNames = columNames;
        this.metaInfos = metaInfos;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public List<String> getColumNames()
    {
        return columNames;
    }

    public void setColumNames(List<String> columNames)
    {
        this.columNames = columNames;
    }

    public List<TableFieldMetaInfo> getMetaInfos()
    {
        return metaInfos;
    }

    public void setMetaInfos(List<TableFieldMetaInfo> metaInfos)
    {
        this.metaInfos = metaInfos;
    }
}
