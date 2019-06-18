package lyj.forward.generation.properties;

import lyj.forward.generation.enums.DdlType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/7 14:22
 */
@ConfigurationProperties("spring.datasource")
public class LyjMySqlProperties
{
    private String url;
    private String username;
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName="com.mysql.jdbc.Driver";

    @Value("${spring.datasource.ddl-auto}")
    private String ddlAuto= DdlType.UPDATE.name();

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDriverClassName()
    {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
    }

    public String getDdlAuto()
    {
        return ddlAuto;
    }

    public void setDdlAuto(String ddlAuto)
    {
        this.ddlAuto = ddlAuto;
    }
}
