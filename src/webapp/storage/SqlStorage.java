package webapp.storage;

import org.postgresql.util.PSQLException;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;
import webapp.sql.ConnectionFactory;
import webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;
    SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        this.sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        String clearSQL = "DELETE FROM resume";
        sqlHelper.usePreparedStatement(clearSQL, ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String getSQL = "SELECT * FROM resume r WHERE r.uuid =?";
        return sqlHelper.usePreparedStatement(getSQL, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        final String updateSQL = "UPDATE resume SET full_name = ? WHERE uuid = ?;";
        sqlHelper.usePreparedStatement(updateSQL, ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        final String saveSQL = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.usePreparedStatement(saveSQL, ps -> {
            try {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            } catch (PSQLException e) { //есть сомнения может выбросится не только если повтор
                throw new ExistStorageException(r.getUuid(), e);
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        final String deleteSQL = "DELETE FROM resume WHERE uuid = ?;";
        sqlHelper.usePreparedStatement(deleteSQL, ps -> {
            ps.setString(1, uuid);
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        final String getAllSortedSQL = "SELECT * FROM resume;";
        List<Resume> listResume = new ArrayList<>();
        return sqlHelper.usePreparedStatement(getAllSortedSQL, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                listResume.add(resume);
            }
            return listResume;
        });
    }

    @Override
    public int size() {
        final String sizeSQL = "SELECT COUNT(*) FROM resume ;";
        return sqlHelper.usePreparedStatement(sizeSQL, ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
