package webapp.storage;

import org.postgresql.util.PSQLException;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        final String updateSQL = "UPDATE resume SET full_name = ? WHERE uuid = ?;";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(updateSQL)) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        final String saveSQL = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(saveSQL)) {

            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (PSQLException e) { //есть сомнения может стовить разное
            throw new ExistStorageException(r.getUuid(), e);
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public void delete(String uuid) {
        final String deleteSQL = "DELETE FROM resume WHERE uuid = ?;";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteSQL)) {
            ps.setString(1,uuid);
            int rs = ps.executeUpdate();

            if (rs == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        final String getAllSortedSQL = "SELECT * FROM resume;";
        List <Resume> listResume = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(getAllSortedSQL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                listResume.add(resume);
            }

            return listResume;

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        ResultSet rs;
        int size;
        final String sizeSQL = "SELECT COUNT(*) FROM resume ;";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sizeSQL)) {
            rs = ps.executeQuery();
            rs.next();
            size = rs.getInt(1);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return size;
    }
}
