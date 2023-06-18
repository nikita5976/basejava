package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.ContactType;
import webapp.model.Resume;
import webapp.model.SectionType;
import webapp.sql.ConnectionFactory;
import webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;
    SqlHelper sqlHelper;


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        this.sqlHelper = new SqlHelper(connectionFactory);
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        String clearSQL = "DELETE FROM resume";
        sqlHelper.usePreparedStatement(clearSQL);
    }

    @Override
    public Resume get(String uuid) {
        String getSQL = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid =? ";
        String getTextSectionSQL = "SELECT * FROM text_section ts WHERE ts.resume_uuid =? ";
        String getListSectionSQL = "SELECT * FROM list_section ls WHERE ls.resume_uuid =? ";
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

            sqlHelper.usePreparedStatement(getTextSectionSQL, psText -> {
                psText.setString(1, uuid);
                ResultSet rsText = psText.executeQuery();
                String personal = rsText.getString("personal");
                if (personal != null) {
                    resume.setSectionPersonal(personal);
                }
                String objective = rsText.getString("objective");
                if (objective != null) {
                    resume.setSectionObjective(objective);
                }
                return null;
            });

            sqlHelper.usePreparedStatement(getListSectionSQL, psList -> {
                psList.setString(1, uuid);
                ResultSet rsList = psList.executeQuery();
                String achievementText = rsList.getString("achievement");
                String qualificationText = rsList.getString("qualification");
                if (achievementText != null) {
                    textToList(achievementText, SectionType.ACHIEVEMENT, resume);
                }
                if (qualificationText != null) {
                    textToList(qualificationText, SectionType.QUALIFICATIONS, resume);
                }
                return null;
            });

            return resume;
        });
    }

    @Override//переделать на save
    public void update(Resume r) {
        final String updateSQL = "UPDATE resume SET full_name = ? WHERE uuid = ?;";
        final String insertUpdateContactSQL = "INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)";

        sqlHelper.usePreparedStatement("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(r.getUuid());
            } else {
                deleteContactAndSection(r.getUuid());
                writeContactAndSection(r);
            }
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        sqlHelper.usePreparedStatement("INSERT INTO resume (full_name, uuid) VALUES (?,?)", ps -> {
            ps.setString(2, r.getUuid());
            ps.setString(1, r.getFullName());
            ps.execute();
            return null;
        });
        writeContactAndSection(r);
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
                Resume resume = get(uuid);
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

    private void deleteContactAndSection(String uuid) {
        String[] tables = {"contact", "text_section", "list_section"};
        for (String table : tables) {
            sqlHelper.usePreparedStatement("DELETE FROM ?  WHERE resume_uuid = ?", ps -> {
                ps.setString(1, table);
                ps.setString(2, uuid);
                ps.execute();
                return null;
            });
        }
    }

    private String listToText(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String text : list) {
            sb.append("\n");
            sb.append(text);
        }
        return sb.toString();
    }

    private void textToList(String text, SectionType type, Resume r) {
        String[] strings = text.split("\n");
        for (String string : strings) {
            switch (type) {
                case ACHIEVEMENT -> r.setSectionAchievement(string);
                case OBJECTIVE -> r.setSectionObjective(string);
            }
        }
    }

    private void writeContactAndSection(Resume r) {

        sqlHelper.transactionalExecute(conn -> {
            writerContact(conn, r);
            String uuid = r.getUuid();

            String sectionPersonal = (r.getSection(SectionType.PERSONAL) == null) ? null : r.getSection(SectionType.PERSONAL).toString();
            String sectionObjective = (r.getSection(SectionType.OBJECTIVE) == null) ? null : r.getSection(SectionType.OBJECTIVE).toString();

            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO text_section (resume_uuid, personal , objective) " +
                    "                                              VALUES (?,?,?)")) {
                ps.setString(1, uuid);
                ps.setString(2, sectionPersonal);
                ps.setString(3, sectionObjective);
                ps.execute();
            }

            List<String> listAchievement = r.getSection(SectionType.ACHIEVEMENT);
            String achievement;
            if (listAchievement != null) {
                achievement = listToText(listAchievement);
            } else {
                achievement = null;
            }
            List<String> listQualification = r.getSection(SectionType.QUALIFICATIONS);
            String qualification;
            if (listQualification != null) {
                qualification = listToText(listQualification);
            } else {
                qualification = null;
            }

            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO list_section (resume_uuid, achievement, qualification)" +
                    "                                              VALUES (?,?,?)")) {
                ps.setString(1, uuid);
                ps.setString(2, achievement);
                ps.setString(3, qualification);
                ps.execute();
            }
/*
            writerSection(conn, r, "text_section", "personal", "objective",
                    sectionPersonal, sectionObjective);
            writerSection(conn, r, "list_section", "achievement", "qualification",
                    achievement, qualification);

 */
            return null;
        });
    }

/*
    private void writerSection(Connection conn, Resume r, String q1, String q2, String q3, String q5, String q6) throws SQLException {
        String uuid = r.getUuid();
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO ? (resume_uuid, ?, ?) VALUES (?,?,?)")) {
            ps.setString(1, q1);
            ps.setString(2, q2);
            ps.setString(3, q3);
            ps.setString(4, uuid);
            ps.setString(5, q5);
            ps.setString(6, q6);
            ps.execute();
        }
    }

 */

    private void writerContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.setString(1, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}
