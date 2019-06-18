package lyj.forward.generation.annotation;

import java.lang.annotation.*;

/**
 * <br>
 * 排除非表中字段
 * @author 永健
 * @since 2019/5/7 15:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LNotTableField
{
}
