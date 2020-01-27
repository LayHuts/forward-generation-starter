package lyj.forward.generation.entity;

import lyj.forward.generation.annotation.LColumn;
import lyj.forward.generation.annotation.LNotTableField;
import lyj.forward.generation.annotation.LTable;
import lyj.forward.generation.annotation.LTableId;
import lyj.forward.generation.entity.entityInfo.EntityFieldMetaInfo;
import lyj.forward.generation.entity.entityInfo.EntityMetaInfo;
import lyj.forward.generation.enums.ColumnType;
import lyj.forward.generation.enums.Type;
import lyj.forward.generation.exception.MyException;
import lyj.forward.generation.parser.Parser;
import lyj.forward.generation.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 12:09
 */
public class EntityParserHandler extends Parser
{
    private static Logger logger = LoggerFactory.getLogger(EntityParserHandler.class);

    private ConfigureEntityTable configureEntity;

    public EntityParserHandler(ConfigureEntityTable configureEntity)
    {
        this.configureEntity = configureEntity;
    }

    @Override
    public void parser(List<Class<?>> entityClass) throws Exception
    {
        logger.info("开始解析实体.....");
        List<EntityMetaInfo> entityList = new ArrayList<>();
        Map<String, EntityMetaInfo> metaInfoHashMap = new HashMap<>();

        // 装表
        List<String> arrayList = new ArrayList<>();

        for (Class<?> aClass : entityClass)
        {
            // 类中的属性
            List<EntityFieldMetaInfo> fieldInfos = new ArrayList<>();
            List<String> columnFields = new ArrayList<>();

            // 表名
            LTable annotation = aClass.getAnnotation(LTable.class);
            String tableName = ConfigureEntityTable.produceTableName(annotation, aClass);
            String entityName = aClass.getSimpleName();

            arrayList.add(tableName);

            // 遍历属性 是否继承了积基类BaseEntity
            checkWhetherExtendSuperClass(aClass, fieldInfos, columnFields);

            // 遍历当前类的属性
            Field[] fields = aClass.getDeclaredFields();

            // 对属性的注解惊醒解析封装成对应数据库的类型
            handlerField(fieldInfos, fields, columnFields);

            // 一个 实体对应一个
            EntityMetaInfo entityMetaInfo = new EntityMetaInfo(entityName, tableName, fieldInfos, columnFields);

            entityList.add(entityMetaInfo);
            metaInfoHashMap.put(tableName, entityMetaInfo);
        }
        configureEntity.setEntityMetaInfoMap(metaInfoHashMap);
        configureEntity.setEntityTableNames(arrayList);
        logger.info("解析完成.....");
    }

    private void handlerFieldInfoChangeCoumn(EntityFieldMetaInfo fieldInfo, Field field) throws Exception
    {
        // 列注解
        LColumn lColumn = field.getAnnotation(LColumn.class);

        // 主键注解
        LTableId lTableId = field.getAnnotation(LTableId.class);

        // 判断字段的属性类型

        String defaultValue = "";
        boolean aNull = true;
        ColumnType columnType = ColumnType.FIELDTYPE;
        ColumnType mappingType = ColumnType.NULL;
        Integer width = 0;
        String remark = null;
        String name = "";

        if (lColumn != null)
        {
            defaultValue = lColumn.defaultValue();
            aNull = lColumn.isNull();
            columnType = lColumn.type();
            mappingType = lColumn.mapping();
            width = lColumn.width();
            remark = lColumn.comment().equals("") ? null : lColumn.comment();
            Class<?> fieldType = field.getType();
            name = fieldType.getSimpleName();

            // 检查类型是否匹配
            checkType(name, columnType);
        }
        else
        {
            Class<?> fieldType = field.getType();
            name = fieldType.getSimpleName();
        }

        // 类型转换匹配
        switch (name)
        {
            case "String":
                columnType = ColumnType.VARCHAR;

                if (width == 0)
                {
                    width = 255;
                }

                if (mappingType != ColumnType.NULL)
                {
                    columnType = lColumn.mapping();
                    width = 0;
                }
                break;
            case "Integer":
                columnType = ColumnType.INT;
                if (width == 0)
                {
                    width = 11;
                }
                break;
            case "int":
                columnType = ColumnType.INT;
                if (width == 0)
                {
                    width = 11;
                }
                break;
            case "Double":
                columnType = ColumnType.DOUBLE;
                break;
            case "double":
                columnType = ColumnType.DOUBLE;
                break;
            case "Long":
                columnType = ColumnType.BIGINT;
                break;
            case "long":
                columnType = ColumnType.BIGINT;
                break;
            case "Float":
                columnType = ColumnType.FLOAT;
                break;
            case "float":
                columnType = ColumnType.FLOAT;
                break;
            case "Date":
                columnType = ColumnType.DATETIME;
                break;
            case "LocalDateTime":
                columnType = ColumnType.DATETIME;
                break;
            case "Boolean":
                columnType = ColumnType.TINYINT;
                width = 2;
                break;
            case "boolean":
                columnType = ColumnType.TINYINT;
                width = 2;
                break;
            case "byte[]":
                columnType = ColumnType.LONGBLOB;
                break;
            case "byte":
                columnType = ColumnType.LONGBLOB;
                break;
            default:
                throw new Exception("数据库找不到对应的字段类型,请使用注解 @LNotTableField 排除掉非表中字段==>" + name);
        }

        fieldInfo.setName(StringUtil.uncapitalizeToUnderLine(field.getName()));
        fieldInfo.setDefaultValue(defaultValue);
        fieldInfo.setNull(aNull);
        fieldInfo.setWidth(width);
        fieldInfo.setType(columnType);
        fieldInfo.setRemark(remark);

        // 是否是主键
        if (lTableId != null)
        {
            fieldInfo.setPrimaryKey(true);
            Type type = lTableId.type();
            fieldInfo.setIdType(type);
            switch (type)
            {
                case AUTO:
                    fieldInfo.setAuto(true);
                    break;
                default:
                    fieldInfo.setAuto(false);
                    break;
            }
        }

    }


    private void checkWhetherExtendSuperClass(Class<?> aClass, List<EntityFieldMetaInfo> fieldInfos, List<String> columnFields) throws Exception
    {
        Class<?> superclass = aClass.getSuperclass();
        if (superclass != null && !superclass.getName().equals("java.lang.Object"))
        {
            Field[] fields = superclass.getDeclaredFields();
            handlerField(fieldInfos, fields, columnFields);
        }
    }

    private void handlerField(List<EntityFieldMetaInfo> entityFfields, Field[] fields, List<String> columnFields) throws Exception
    {
        for (Field field : fields)
        {
            // 判断属性注解 是否是表中字段
            LNotTableField lTableField = field.getAnnotation(LNotTableField.class);


            int modifiers = field.getModifiers();
            List<String> list = Arrays.asList(Modifier.toString(modifiers).split(" "));
            if (list.contains("transient") || list.contains("static"))
            {
                continue;
            }


            // 排除serialVersionUID
            if (lTableField != null)
            {
                continue;
            }

            // 如果不是表中字段 ️没有加注解 比如 String[] 数组这一种
            filterFIleTypeIsArrayOrList(field);

            // 字段信息
            EntityFieldMetaInfo fieldInfo = new EntityFieldMetaInfo();
            fieldInfo.setName(StringUtil.uncapitalizeToUnderLine(field.getName()));
            columnFields.add(StringUtil.uncapitalizeToUnderLine(field.getName()));
            handlerFieldInfoChangeCoumn(fieldInfo, field);
            entityFfields.add(fieldInfo);
        }
    }


    /**
     * 检查非表中字段
     *
     * @param field
     */
    private void filterFIleTypeIsArrayOrList(Field field) throws Exception
    {
        String tip = "";
        Class<?> type = field.getType();
        if (type.isArray())
        {
            if (type == byte[].class)
            {
                return;
            }
            tip = "数组";
        }
        else if (type == List.class)
        {
            tip = "List[]";
        }
        String msg = "实体属性 " + field.getName() + " 出现 " + tip + " 类型数据，无法生成对应的字段，请加上 @LNotTableField 注解，排除掉非表中字段";
        if (!"".equals(tip))
        {
            throw new Exception(msg);
        }
    }


    /**
     * <br>
     * 检查类型是否匹配
     */
    private void checkType(String name, ColumnType type) throws Exception
    {
        // 校验规则
        if (("String".equals(name) && type == ColumnType.DATE)
                || "String".equals(name) && type == ColumnType.TIME
                || "String".equals(name) && type == ColumnType.DATETIME
                || "String".equals(name) && type == ColumnType.YEAR
        )
        {
            throw new Exception("[ " + name + " ]类型与[ " + type.name() + " ]不匹配");
        }
    }


}
