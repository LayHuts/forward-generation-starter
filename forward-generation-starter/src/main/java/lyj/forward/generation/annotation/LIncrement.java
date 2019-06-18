package lyj.forward.generation.annotation;

import lyj.forward.generation.enums.IdType;

import java.lang.annotation.*;

/**
 * <br>
 * 是否自增
 * @author 永健
 * @since 2019/5/7 15:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LIncrement
{
    IdType type() default IdType.NONE;
}
