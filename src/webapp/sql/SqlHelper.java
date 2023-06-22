package webapp.sql;

import webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void usePreparedStatement(String sqlAction) {
        usePreparedStatement(sqlAction, PreparedStatement::execute);
    }

    public <T> T usePreparedStatement(String sqlAction, ABlockOfCode<T> aBlockOfCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlAction)) {
            return aBlockOfCode.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T usePreparedStatement(Connection conn, String sqlAction, ABlockOfCode<T> aBlockOfCode ) {
        try (PreparedStatement ps = conn.prepareStatement(sqlAction)) {
            return aBlockOfCode.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> sqlTransaction) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = sqlTransaction.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
