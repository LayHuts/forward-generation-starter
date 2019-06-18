package lyj.forward.generation.annotation;

import java.lang.annotation.*;

/**
 * <br>
 * 实体注解 指定表名否则转下划线
 * @author 永健
 * @since 2019/5/7 15:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface LTable
{
  String name() default "";
}
