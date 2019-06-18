package lyj.forward.generation.excutor;

import lyj.forward.generation.Constanst;
import lyj.forward.generation.entity.entityInfo.EntityFieldMetaInfo;
import lyj.forward.generation.enums.DdlSql;
import lyj.forward.generation.jdbc.JdbcTransation;
import lyj.forward.generation.jdbc.LJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 13:44
 */
public class DdlExecutor implements Excutor
{

    private LJdbc lJdbc;

    public DdlExecutor(LJdbc lJdbc)
    {
        this.lJdbc = lJdbc;
    }

    @Override
    public int update(String ddlSql) throws SQLException
    {
        return lJdbc.executeUpdate(ddlSql);
    }

    @Override
    public ResultSet query(DdlSql ddlSql, String param)
    {
        return null;
    }

    @Override
    public PreparedStatement preparedStatementquery(DdlSql ddlSql, String param)
    {
        String sql = String.format(ddlSql.getDdlSql(), param);
        return lJdbc.executePreparedStatement(sql);
    }

    @Override
    public void commit()
    {
        JdbcTransation.commit(connection());
        lJdbc.close();
    }



    @Override
    public void rollback()
    {
        JdbcTransation.rooback(connection());
        lJdbc.close();
    }

    @Override
    public Connection connection()
    {
        return lJdbc.getConnection();
    }

}
