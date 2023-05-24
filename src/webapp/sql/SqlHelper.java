package webapp.sql;

import webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper (ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T usePreparedStatement (String sqlAction, ABlockOfCode<T> aBlockOfCode){
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlAction)) {
            T returnValue = aBlockOfCode.execute(ps);
            return returnValue;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
