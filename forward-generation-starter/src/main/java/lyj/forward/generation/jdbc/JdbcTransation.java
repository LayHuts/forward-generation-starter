package lyj.forward.generation.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 14:16
 */
public class JdbcTransation
{

    public static void commit(Connection connection)
    {
        try {
            if (connection.getAutoCommit()){
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rooback(Connection connection)
    {
        try {
            if (connection.getAutoCommit()){
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
