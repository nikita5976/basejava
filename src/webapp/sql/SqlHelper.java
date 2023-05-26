package webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public record SqlHelper(ConnectionFactory connectionFactory) {

    public <T> T usePreparedStatement(String sqlAction, ABlockOfCode<T> aBlockOfCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlAction)) {
            T returnValue = aBlockOfCode.execute(ps);
            return returnValue;
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
}
