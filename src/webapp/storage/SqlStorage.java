package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.ContactType;
import webapp.model.Resume;
import webapp.sql.ConnectionFactory;
import webapp.sql.SqlHelper;
import webapp.sql.SqlWriter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        sqlHelper.usePreparedStatement(clearSQL);
    }

    @Override
    public Resume get(String uuid) {
        String getSQL =
                "       SELECT * FROM resume r " +
                        "    LEFT JOIN contact c " +
                        "           ON r.uuid = c.resume_uuid " +
                        "        WHERE r.uuid =? ";
        return sqlHelper.usePreparedStatement(getSQL, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                String contactType = rs.getString("type");
                if (contactType != null) {
                    ContactType type = ContactType.valueOf(contactType);
                    resume.addContact(type, value);
                }
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void update(Resume r) {
        final String updateSQL = "UPDATE resume SET full_name = ? WHERE uuid = ?;";
        final String updateContactSQL = "UPDATE contact SET  value =? WHERE resume_uuid = ?  AND type =? ";
        final String insertUpdateContactSQL = "INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)";
        SqlWriter.sqlWriter(updateSQL, updateContactSQL , sqlHelper, r, ps -> {
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        final String saveSQL = "INSERT INTO resume (full_name, uuid) VALUES (?,?)";
        final String saveContactSQL = "INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)";

        SqlWriter.sqlWriter(saveSQL, saveContactSQL, sqlHelper, r, PreparedStatement::execute);
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
        final String getAllSortedSQL = "SELECT * FROM resume r ORDER BY uuid;";
        List<Resume> listResume = new ArrayList<>();
        return sqlHelper.usePreparedStatement(getAllSortedSQL, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                sqlHelper.usePreparedStatement("SELECT * FROM contact WHERE resume_uuid = ?;", ps1 -> {
                    ps1.setString(1, uuid);
                    ResultSet rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        ContactType type = ContactType.valueOf(rs1.getString("type"));
                        String value = rs1.getString("value");
                        resume.addContact(type, value);
                    }
                    return null;
                });
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
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

}
