package lyj.forward.generation.parser;

import lyj.forward.generation.enums.ColumnType;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 11:48
 */
public abstract class Parser
{
    protected void parser(List<Class<?>> entityClass){};
    protected void parser(){};
}
