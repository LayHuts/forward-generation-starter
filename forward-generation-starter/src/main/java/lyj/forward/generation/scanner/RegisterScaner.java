package lyj.forward.generation.scanner;

import lyj.forward.generation.annotation.EnableAutoForwardGeneration;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * <br>
 * 扫描实体类
 *
 * @author 永健
 * @since 2019/5/9 10:48
 */
public class RegisterScaner implements ImportBeanDefinitionRegistrar
{
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry)
    {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableAutoForwardGeneration.class.getName());

        // 判断是否开启
        Boolean onOff = (Boolean) attributes.get("OnOff");

        if (onOff) {
            String packages = (String) attributes.get("entityPackages");
            EntityClassPathScanner scanner = new EntityClassPathScanner(registry);
            scanner.registerDefaultFilters();
            scanner.doScan(packages);
        }

//        AnnotaionParserScanner parserScanner = new AnnotaionParserScanner(registry);
//        parserScanner.doScan("lyj.forward.generation.annotaionparser");
    }
}
