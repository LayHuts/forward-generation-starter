package lyj.forward.generation.annotation;

import lyj.forward.generation.enums.ColumnType;

import java.lang.annotation.*;

/**
 * <br>
 * 列注解
 * @author 永健
 * @since 2019/5/7 14:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LColumn
{
    /**
     * <br>
     * 列长度 String 类型的默认为
     */
    int width() default 0;

    /**
     * <br>
     * 指定数据库类型
     */
    ColumnType type() default ColumnType.FIELDTYPE;

    /**
     * <br>
     * 列是否默认为空
     */
    boolean isNull() default true;

    /**
     * <br>
     * 是否有默认值
     */
    String defaultValue() default "";

    /**
     * <br>
     * 注释
     */
    String comment() default "";

}
