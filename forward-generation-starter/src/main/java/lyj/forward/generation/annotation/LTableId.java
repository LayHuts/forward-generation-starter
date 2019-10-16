package lyj.forward.generation.annotation;

import lyj.forward.generation.enums.Type;

import java.lang.annotation.*;

/**
 * <br>
 * 主键注解
 *
 * @author 永健
 * @since 2019/5/7 14:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LTableId
{
    Type type() default Type.NONE;
}
