package lyj.forward.generation.database;

import lyj.forward.generation.Constanst;
import lyj.forward.generation.database.table.TableFieldMetaInfo;
import lyj.forward.generation.database.table.TableMetaInfo;
import lyj.forward.generation.entity.ConfigureEntityTable;
import lyj.forward.generation.enums.ColumnType;
import lyj.forward.generation.enums.DdlSql;
import lyj.forward.generation.exception.MyException;
import lyj.forward.generation.excutor.DdlExecutor;
import lyj.forward.generation.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 13:30
 */
public class TableParserHandler extends Parser
{

    private static Logger logger = LoggerFactory.getLogger(TableParserHandler.class);

    private ConfigureEntityTable configureEntityTable;
    private DdlExecutor ddlExecutor;

    public TableParserHandler(ConfigureEntityTable configureEntityTable, DdlExecutor ddlExecutor)
    {
        this.configureEntityTable = configureEntityTable;
        this.ddlExecutor = ddlExecutor;
    }

    @Override
    public void parser()
    {

        logger.info("获取数据库中原有的表数据......");
        // 读取当前数据库的表
        setTableName();

        // 读取表中的字段属性
        handlerTableCoumn();

        logger.info("获取完成......");
    }

    /**
     * <br>
     * 读取数据库中的所有表
     */
    private void setTableName()
    {
        List<String> tableNames = new ArrayList<>();
        ResultSet tables = null;
        List<TableMetaInfo> dataBaseTables = new ArrayList<>();
        try {
            tables = ddlExecutor.connection().getMetaData().getTables(getDataBaseName(), null, null, new String[]{"TABLE", "VIEW"});
            while (tables.next()) {
                tableNames.add(tables.getString(Constanst.TABLE_NAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.configureEntityTable.setTableNames(tableNames);
    }


    private void handlerTableCoumn()
    {
        try {
            List<String> tableNames = this.configureEntityTable.getTableNames();
            Map<String, TableMetaInfo> metaInfoHashMap = new HashMap<>();

            for (String tableName : tableNames) {

                // 一张表对应列的集合
                List<String> columuNames = new ArrayList<>();

                ResultSet resultSet = this.ddlExecutor.preparedStatementquery(DdlSql.SHOW_COLUM, tableName).executeQuery();

                List<TableFieldMetaInfo> metaInfos = new ArrayList<>();

                // 数据库中的字段类型 取出来与实体类比较是否需要更改或者添加
                while (resultSet.next()) {
                    TableFieldMetaInfo baseColum = new TableFieldMetaInfo();

                    // 列名
                    String colunmName = resultSet.getString(Constanst.FIELD);
                    baseColum.setName(colunmName);

                    // 类型 int(11)
                    String type = resultSet.getString(Constanst.COLUMN_TYPE);
                    String width = "";
                    for (int i = 0; i < type.length(); i++) {
                        if (type.charAt(i) >= 48 && type.charAt(i) <= 57) {
                            width += type.charAt(i);
                        }
                    }
                    type = type.substring(0, type.indexOf("(") == -1 ? type.length() : type.indexOf("("));


                    // 列长度
                    baseColum.setWidth(width.equals("") ? 0 : Integer.parseInt(width));

                    // 列类型
                    baseColum.setType(getCoumType(type));

                    // 是否为空
                    baseColum.setNull("YES".equals(resultSet.getString(Constanst.NULLABLE)) ? true : false);

                    // 默认值
                    baseColum.setDefaultValue(resultSet.getString(Constanst.COLUMN_DEF));

                    // 是否是主键

                    if ("PRI".equals(resultSet.getString(Constanst.COLUMN_PK))) {
                        baseColum.setPrimaryKey(true);
                    }

                    // 是否自增
                    baseColum.setAuto(resultSet.getString(Constanst.IS_AUTOINCREMENT).equals("auto_increment") ? true : false);

                    // 列注释
                    baseColum.setRemark(resultSet.getString(Constanst.COMMENT));

                    columuNames.add(colunmName);
                    metaInfos.add(baseColum);
                }
                TableMetaInfo dataBaseTable = new TableMetaInfo(tableName, columuNames, metaInfos);
                metaInfoHashMap.put(tableName, dataBaseTable);
            }
            this.configureEntityTable.setTableMetaInfoMap(metaInfoHashMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private ColumnType getCoumType(String typeName)
    {
        typeName = typeName.toUpperCase();
        switch (typeName) {
            case "VARCHAR":
                return ColumnType.VARCHAR;
            case "INT":
                return ColumnType.INT;
            case "TEXT":
                return ColumnType.TEXT;
            case "TINYINT":
                return ColumnType.TINYINT;

            case "SMALLINT":
                return ColumnType.SMALLINT;

            case "MEDIUMINT":
                return ColumnType.MEDIUMINT;

            case "BIGINT":
                return ColumnType.BIGINT;

            case "FLOAT":
                return ColumnType.FLOAT;

            case "DOUBLE":
                return ColumnType.DOUBLE;

            case "DECIMAL":
                return ColumnType.DECIMAL;

            case "DATE":
                return ColumnType.DATE;

            case "TIME":
                return ColumnType.TIME;

            case "YEAR":
                return ColumnType.YEAR;

            case "DATETIME":
                return ColumnType.DATETIME;

            case "TIMESTAMP":
                return ColumnType.TIMESTAMP;

            case "MEDIUMBLOB":
                return ColumnType.MEDIUMBLOB;

            case "TINYBLOB":
                return ColumnType.TINYBLOB;

            case "TINYTEXT":
                return ColumnType.TINYTEXT;

            case "BLOB":
                return ColumnType.BLOB;

            case "MEDIUMTEXT":
                return ColumnType.MEDIUMTEXT;


            case "LONGBLOB":
                return ColumnType.LONGBLOB;

            case "LONGTEXT":
                return ColumnType.LONGTEXT;

            case "SHORT":
                return ColumnType.SHORT;

            default:
                return null;
        }
    }


    private String getDataBaseName()
    {
        Connection connection = ddlExecutor.connection();
        try {
            return connection.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException("找不到数据库");
        }
    }

}
