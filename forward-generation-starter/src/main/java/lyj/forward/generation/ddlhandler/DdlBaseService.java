package lyj.forward.generation.ddlhandler;

import lyj.forward.generation.commominfo.FieldInfo;
import lyj.forward.generation.database.table.TableFieldMetaInfo;
import lyj.forward.generation.database.table.TableMetaInfo;
import lyj.forward.generation.entity.ConfigureEntityTable;
import lyj.forward.generation.entity.entityInfo.EntityFieldMetaInfo;
import lyj.forward.generation.entity.entityInfo.EntityMetaInfo;
import lyj.forward.generation.enums.ColumnType;
import lyj.forward.generation.enums.DdlSql;
import lyj.forward.generation.excutor.Excutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/8 15:01
 */
public abstract class DdlBaseService
{
    private static Logger logger =LoggerFactory.getLogger(DdlBaseService.class);

    protected Excutor excutor;

    abstract Excutor getExcutor();

    abstract void excute();

    /**
     * <br>
     * 删除表
     */
    protected void dropTable(List<String> tableNames) throws SQLException
    {
        for (String tableName : tableNames) {
            getExcutor().update(String.format(DdlSql.DROP_TABLE.getDdlSql(), tableName));
        }
    }


    protected void createTable(Map<String,EntityMetaInfo> entityMetaInfoMap)
    {
        for (Map.Entry<String, EntityMetaInfo> entry : entityMetaInfoMap.entrySet()) {
            EntityMetaInfo entityMetaInfo = entry.getValue();
            // 不存在则创建
            String ddlSql = installCreateTableDdlSql(DdlSql.CREATE_TABLE, entityMetaInfo);
            try {
                getExcutor().update(ddlSql);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(ddlSql);
            }
        }
    }

    /**
     * <br>
     * 创建表
     */
    protected void createTable(List<String> tableNames, ConfigureEntityTable configureEntityTable, Map<String,EntityMetaInfo> entityMetaInfoMap) throws SQLException
    {
        for (Map.Entry<String, EntityMetaInfo> entry : entityMetaInfoMap.entrySet()) {

            String tableName=entry.getKey();
            EntityMetaInfo entityMetaInfo = entry.getValue();

            // 判断表是否存在
            if (!tableNames.contains(tableName)) {
                // 不存在则创建
                String ddlSql = installCreateTableDdlSql(DdlSql.CREATE_TABLE, entityMetaInfo);
                getExcutor().update(ddlSql);
            } else {
                // 存在 就更新

                // 当前实体的属性
                List<EntityFieldMetaInfo> fieldInfos = entityMetaInfo.getFieldInfos();


                // 一个个属性去更新
                for (EntityFieldMetaInfo fieldMetaInfo : fieldInfos) {

                    // 先判断表里是否存在字段
                    TableMetaInfo tabledInfo = configureEntityTable.getTabledInfo(tableName);
                    List<String> columNames = tabledInfo.getColumNames();

                    if (columNames.contains(fieldMetaInfo.getName())) {
                        // 已经存在字段 执行更新

                        String sqlS = installAlterTableField(DdlSql.ALTER_TABLE, fieldMetaInfo, tableName);
                        getExcutor().update(sqlS);
                    } else {
                        // 不存在 新增字段
                        String sqlS = installAlterTableField(DdlSql.ALTER_TABLE_ADD, fieldMetaInfo, tableName);
                        getExcutor().update(sqlS);
                    }
                }
            }
        }
    }


    /**
     * <br>
     * 组装创建表的Sql语句
     */
    protected String installCreateTableDdlSql(DdlSql sql, EntityMetaInfo entityMetaInfo)
    {

        StringBuffer buffer = new StringBuffer();
        StringBuffer primary = new StringBuffer();

        // 表名
        String tableName = entityMetaInfo.getTableName();

        // 属性字段元数据
        List<EntityFieldMetaInfo> fieldInfos = entityMetaInfo.getFieldInfos();
        for (EntityFieldMetaInfo field : fieldInfos) {
            // 字段名
            String columName = field.getName();

            // 类型
            String type = field.getType().name();

            // 长度
            Integer width = field.getWidth();

            // 是否为空
            boolean aNull = field.isNull();

            // 是否自增
            boolean auto = field.isAuto();

            // 是否有默认值
            Object defaultValue = field.getDefaultValue();

            // 是否是主键
            boolean primaryKey = field.isPrimaryKey();

            if (primaryKey) {
                primary.append(columName + ",");
            }

            // 备注
            String remark = field.getRemark();

            // 字段
            buffer.append(columName + " ");

            // 类型
            buffer.append(type);
            if (width > 0) {
                buffer.append("(" + width + ")");
            }

            if (!aNull) {
                buffer.append(" NOT NULL");
            }

            if (auto) {
                buffer.append(" AUTO_INCREMENT");
            }

            // 默认值
            if (defaultValue.equals("")) {
                if (primaryKey) {
                } else {
                    buffer.append(" DEFAULT NULL");
                }
            } else {
                buffer.append(" DEFAULT " + "\'" + defaultValue + "\'");
            }

            // 注释
            if (remark != null) {
                buffer.append(" COMMENT " + "\'" + remark + "\'");
            }

            buffer.append(",");
        }
        buffer.append("PRIMARY KEY (" + primary.toString().substring(0, primary.toString().length() - 1) + ")");

        String sqlTempParam = buffer.toString();

        return String.format(sql.getDdlSql(), tableName, sqlTempParam);
    }

    /**
     * <br>
     * 组装更改列的sql
     */
    protected String installAlterTableField(DdlSql sql, EntityFieldMetaInfo fieldInfo, String tableName)
    {
        StringBuffer buffer = new StringBuffer();

        // 字段名
        String columName = fieldInfo.getName();


        if (columName == null || columName.equals("")) {
            columName = fieldInfo.getName();
        }

        // 类型
        String type = fieldInfo.getType().name();

        // 长度
        Integer width = fieldInfo.getWidth();

        // 是否为空
        boolean aNull = fieldInfo.isNull();

        // 是否自增
        boolean auto = fieldInfo.isAuto();

        // 是否有默认值
        Object defaultValue = fieldInfo.getDefaultValue();

        // 是否是主键
        boolean primaryKey = fieldInfo.isPrimaryKey();


        // 备注
        String remark = fieldInfo.getRemark();

        // 字段
        buffer.append(columName + " ");

        // 类型
        buffer.append(type);
        if (width > 0) {
            buffer.append("(" + width + ")");
        }

        if (!aNull) {
            buffer.append(" NOT NULL");
        }

        if (auto) {
            buffer.append(" AUTO_INCREMENT");
        }


        if (defaultValue.equals("")) {
            if (primaryKey) {
            } else {
                buffer.append(" DEFAULT NULL");
            }
        } else {
            buffer.append(" DEFAULT " + "\'" + defaultValue + "\'");
        }

        if (remark != null) {
            buffer.append(" COMMENT " + "\'" + remark + "\'");
        }

        String sqlTempParam = buffer.toString();

        return String.format(sql.getDdlSql(), tableName, sqlTempParam);
    }

    private String getCoumType(ColumnType type)
    {
        switch (type) {
            case VARCHAR:
                return "VARCHAR";
            case INT:
                return "INT";
            case TEXT:
                return "TEXT";
            case TINYINT:
                return "TINYINT";

            case SMALLINT:
                return "SMALLINT";

            case MEDIUMINT:
                return "MEDIUMINT";

            case BIGINT:
                return "BIGINT";

            case FLOAT:
                return "FLOAT";

            case DOUBLE:
                return "DOUBLE";

            case DECIMAL:
                return "DECIMAL";

            case DATE:
                return "DATE";

            case TIME:
                return "TIME";

            case YEAR:
                return "YEAR";

            case DATETIME:
                return "DATETIME";

            case TIMESTAMP:
                return "TIMESTAMP";

            case MEDIUMBLOB:
                return "MEDIUMBLOB";

            case TINYBLOB:
                return "TINYBLOB";

            case TINYTEXT:
                return "TINYTEXT";

            case BLOB:
                return "BLOB";

            case MEDIUMTEXT:
                return "MEDIUMTEXT";


            case LONGBLOB:
                return "LONGBLOB";

            case LONGTEXT:
                return "LONGTEXT";

            case SHORT:
                return "SHORT";
            default:
                return "VARCHAR";
        }
    }
}
