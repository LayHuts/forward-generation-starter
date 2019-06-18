package lyj.forward.generation.scanner;

import lyj.forward.generation.annotation.LTable;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/7 15:27
 */
public class EntityClassPathScanner extends ClassPathBeanDefinitionScanner
{
    /**
     * @param registry = false 不使用默认的扫描过滤器
     */
    public EntityClassPathScanner(BeanDefinitionRegistry registry)
    {
        super(registry, false);
    }

    /**
     * 利用Spring扫描器将包下的类封装成BeanDefinitionHolder
     *
     * @param basePackages
     * @return
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages)
    {
        logger.info("$$$$$$ 开始扫描自定义注解包下的类...");
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        logger.info("$$$$$$ 扫描完成，共有 "+ beanDefinitions.size() +" 个");
        return beanDefinitions;
    }

    /**
     * 重写这个 isCandidateComponent 方法 判断自己要扫描的类型
     *
     * @param beanDefinition
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition)
    {
        // 不是接口 并且加油 @LTable注解的类
        return beanDefinition.getMetadata().hasAnnotation(LTable.class.getName()) && !beanDefinition.getMetadata().isInterface();
    }

    /**
     * 重写过滤器 用自己的排除规则
     */
    @Override
    public void registerDefaultFilters()
    {
        /**
         * 扫描过滤器
         * 扫码当前包下的所有的类
         */
        this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

        /**
         * 排除 package-commominfo.java
         */
        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-commominfo");
        });
    }
}
