package lyj.forward.generation.jdbc;

import lyj.forward.generation.properties.LyjMySqlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/7 14:30
 */
public class LJdbc
{
    private static Logger logger = LoggerFactory.getLogger(LJdbc.class);

    private LyjMySqlProperties mysqlProperties;

    private  Connection connection = null;
    private  PreparedStatement preparedStatement = null;

    public LJdbc(LyjMySqlProperties mysqlProperties)
    {
        this.mysqlProperties = mysqlProperties;
    }

    public Connection getConnection()
    {
        if (connection == null) {
            try {
                Class.forName(this.mysqlProperties.getDriverClassName());
                logger.info("@_@!  数据库驱动加载成功");
                connection = DriverManager.getConnection(this.mysqlProperties.getUrl(), this.mysqlProperties.getUsername(), this.mysqlProperties.getPassword());
                connection.setAutoCommit(false);
                logger.info("@_@!  数据库连接成功");
            } catch (Exception e) {
                logger.info("@_@!  数据库连接失败," + e);
                e.printStackTrace();
            }
        }
        return connection;
    }


    private PreparedStatement getPreparedStatement(String sql)
    {
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdate(String sql) throws SQLException
    {
        return getPreparedStatement(sql).executeUpdate();
    }

    public ResultSet executeQuery(String sql)
    {
        ResultSet resultSet = null;
        try {
            resultSet = getPreparedStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(sql);
        }
        return resultSet;
    }


    public PreparedStatement executePreparedStatement(String sql)
    {
        return  getPreparedStatement(sql);
    }

    public void close()
    {
        try {
            if (connection != null) {
                connection.close();
            }

            if (preparedStatement!=null){
                preparedStatement.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
