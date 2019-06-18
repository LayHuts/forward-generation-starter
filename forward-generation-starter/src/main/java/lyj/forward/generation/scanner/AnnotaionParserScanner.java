package lyj.forward.generation.scanner;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * <br>
 * 用于扫描 LTableParser 类将其注入Spring中 否则此类的@Compent注解不会被扫描到
 * @author 永健
 * @since 2019/5/9 11:28
 */
//public class AnnotaionParserScanner extends ClassPathBeanDefinitionScanner
//{
//    public AnnotaionParserScanner(BeanDefinitionRegistry registry)
//    {
//        super(registry);
//    }
//
//    @Override
//    protected Set<BeanDefinitionHolder> doScan(String... basePackages)
//    {
//        return super.doScan(basePackages);
//    }
//}
