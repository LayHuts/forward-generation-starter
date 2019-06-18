package lyj.forward.generation.enums;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/7 14:51
 */
public enum DdlSql
{
    DROP_TABLE("DROP TABLE %s;","删除表DDL语句")
    ,CREATE_TABLE("CREATE TABLE %s ( %s ) ENGINE=InnoDB DEFAULT CHARSET=utf8;","创建表的DDl语句")
    ,ALTER_TABLE("ALTER TABLE %s modify %s;","修改表字段的DDL语句")
    ,ALTER_TABLE_ADD("ALTER TABLE %s ADD %s;","新增字段")
    ,SHOW_COLUM("show full columns from %s","获取数据库的字段");

    private String ddlSql;
    private String desc;

    DdlSql(String ddlSql, String desc){
        this.ddlSql=ddlSql;
        this.desc=desc;
    }

    public String getDdlSql()
    {
        return ddlSql;
    }

    public void setDdlSql(String ddlSql)
    {
        this.ddlSql = ddlSql;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }}
