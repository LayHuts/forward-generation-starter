package lyj.forward.generation.commominfo;

import lyj.forward.generation.enums.ColumnType;
import lyj.forward.generation.enums.IdType;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/8 13:03
 */
public class FieldInfo
{
    /**
     * <br>
     * 字段名
     */
    private String name;

    /**
     * <br>
     *  字段类型
     */
    private ColumnType type;

    /**
     * <br>
     * 默认的属性类型
     */
    private Class<?> typeClass;

    /**
     * <br>
     * 长度
     */
    private Integer width;

    /**
     * <br>
     * 字段是否为空
     */
    private boolean isNull;

    /**
     * <br>
     * 字段是默认值
     */
    private Object defaultValue;

    /**
     * <br>
     * 是否为主键
     */
    private boolean primaryKey=false;

    /**
     * <br>
     * 类型
     */
    private IdType idType;

    /**
     * <br>
     * 注释
     */
    private String remark;

    /**
     * <br>
     * 是否自增
     */
    private boolean isAuto;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ColumnType getType()
    {
        return type;
    }

    public void setType(ColumnType type)
    {
        this.type = type;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public boolean isNull()
    {
        return isNull;
    }

    public void setNull(boolean aNull)
    {
        isNull = aNull;
    }

    public Object getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public boolean isPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public IdType getIdType()
    {
        return idType;
    }

    public void setIdType(IdType idType)
    {
        this.idType = idType;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public boolean isAuto()
    {
        return isAuto;
    }

    public void setAuto(boolean auto)
    {
        isAuto = auto;
    }

    public Class<?> getTypeClass()
    {
        return typeClass;
    }

    public void setTypeClass(Class<?> typeClass)
    {
        this.typeClass = typeClass;
    }
}
