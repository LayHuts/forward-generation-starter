package lyj.forward.generation.annotation;

import lyj.forward.generation.scanner.RegisterScaner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <br>
 * 开启正向生成操作
 * @author 永健
 * @since 2019/5/7 14:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RegisterScaner.class)
public @interface EnableAutoForwardGeneration
{
    String entityPackages();
    boolean  OnOff() default true;
}
