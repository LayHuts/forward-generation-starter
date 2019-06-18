package lyj.forward.generation.excutor;

import lyj.forward.generation.entity.entityInfo.EntityFieldMetaInfo;
import lyj.forward.generation.enums.DdlSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/9 13:38
 */
public interface Excutor
{
    int update(String ddlSql) throws SQLException;

    ResultSet query(DdlSql ddlSql, String param);

    PreparedStatement preparedStatementquery(DdlSql ddlSql, String param);

    void commit() throws SQLException;

    void rollback() throws SQLException;

    Connection connection();
}
