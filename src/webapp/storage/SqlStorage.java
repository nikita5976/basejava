package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.*;
import webapp.sql.ConnectionFactory;
import webapp.sql.SqlHelper;
import webapp.util.JsonParser;

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
        String idRestart = "ALTER SEQUENCE contact_id_seq RESTART WITH 1;" +
                "UPDATE contact SET id=nextval('contact_id_seq');" +
                "ALTER SEQUENCE text_section_id_seq RESTART WITH 1;" +
                "UPDATE text_section SET id=nextval('contact_id_seq');" +
                "ALTER SEQUENCE list_section_id_seq RESTART WITH 1;" +
                "UPDATE list_section SET id=nextval('contact_id_seq');" +
                "ALTER SEQUENCE company_section_id_seq RESTART WITH 1;" +
                "UPDATE company_section SET id=nextval('contact_id_seq');";
        sqlHelper.usePreparedStatement(clearSQL);
        sqlHelper.usePreparedStatement(idRestart);

    }

    @Override
    public Resume get(String uuid) {
        String getSQL = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid =? ";
        String getTextSectionSQL = "SELECT * FROM text_section ts WHERE ts.resume_uuid =? ";
        String getListSectionSQL = "SELECT * FROM list_section ls WHERE ls.resume_uuid =? ";
        String getCompanySectionSQL = "SELECT * FROM company_section ls WHERE ls.resume_uuid =? ";
        return sqlHelper.transactionalExecute(conn -> sqlHelper.usePreparedStatement(conn, getSQL, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            addContact(rs, resume);
            addTextSection(conn, getTextSectionSQL, resume, uuid);
            addListSection(conn, getListSectionSQL, resume, uuid);
            addCompanySection(conn, getCompanySectionSQL, resume, uuid);
            return resume;
        }));
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            String uuid = r.getUuid();
            sqlHelper.usePreparedStatement(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                int rs = ps.executeUpdate();
                if (rs == 0) {
                    throw new NotExistStorageException(uuid);
                } else {
                    deleteAttributes(uuid, "DELETE FROM contact  WHERE resume_uuid = ?");
                    deleteAttributes(uuid, "DELETE FROM text_section  WHERE resume_uuid = ?");
                    deleteAttributes(uuid, "DELETE FROM list_section  WHERE resume_uuid = ?");
                    deleteAttributes(uuid, "DELETE FROM company_section  WHERE resume_uuid = ?");
                    writerContact(conn, r);
                    writerTextSection(conn, r);
                    writerListSection(conn, r);
                    writerCompanySection(conn, r);
                }
                return null;
            });
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            sqlHelper.usePreparedStatement(conn, "INSERT INTO resume (full_name, uuid) VALUES (?,?)", ps -> {
                ps.setString(2, r.getUuid());
                ps.setString(1, r.getFullName());
                ps.execute();
                return null;
            });
            writerContact(conn, r);
            writerTextSection(conn, r);
            writerListSection(conn, r);
            writerCompanySection(conn, r);
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

    @Override// переделать на мапу, брать только uuid и full name
    public List<Resume> getAllSorted() {
        final String getAllSortedSQL = "SELECT * FROM resume r ORDER BY uuid;";
        return sqlHelper.transactionalExecute(conn -> {
            List<Resume> listResume = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(getAllSortedSQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = get(uuid);
                    listResume.add(resume);
                }
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

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        do {
            String value = rs.getString("value");
            String contactType = rs.getString("type");
            if (contactType != null) {
                ContactType type = ContactType.valueOf(contactType);
                resume.setContact(type, value);
            }
        } while (rs.next());
    }

    private void addTextSection(Connection conn, String getTextSectionSQL, Resume resume, String uuid) {
        sqlHelper.usePreparedStatement(conn, getTextSectionSQL, psText -> {
            psText.setString(1, uuid);
            ResultSet rsText = psText.executeQuery();
            if (rsText.next()) {
                String personal = rsText.getString("personal");
                if (personal != null) {
                    resume.setSectionPersonal(personal);
                }
            }
            String objective = rsText.getString("objective");
            if (objective != null) {
                resume.setSectionObjective(objective);
            }
            return null;
        });
    }

    private void addListSection(Connection conn, String getListSectionSQL, Resume resume, String uuid) {
        sqlHelper.usePreparedStatement(conn, getListSectionSQL, psList -> {
            psList.setString(1, uuid);
            ResultSet rsList = psList.executeQuery();
            rsList.next();
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
    }

    private void addCompanySection(Connection conn, String getCompanySectionSQL, Resume resume, String uuid) {
        sqlHelper.usePreparedStatement(conn, getCompanySectionSQL, psCompany -> {
            psCompany.setString(1, uuid);
            ResultSet rsCompany = psCompany.executeQuery();
            rsCompany.next();
            String jsonEducation = rsCompany.getString("education");
            String jsonExperience = rsCompany.getString("experience");
            if (jsonEducation != null) {
                CompanySection education = JsonParser.read(jsonEducation, CompanySection.class);
                resume.setSection(SectionType.EDUCATION, education);
            }
            if (jsonExperience != null) {
                CompanySection experience = JsonParser.read(jsonExperience, CompanySection.class);
                resume.setSection(SectionType.EXPERIENCE, experience);
            }
            return null;
        });
    }

    private void deleteAttributes(String uuid, String sqlAction) {
        sqlHelper.usePreparedStatement(sqlAction, ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    private String listToText(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String text : list) {
            sb.append(text);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void textToList(String text, SectionType type, Resume r) {
        String[] strings = text.split("\n");
        for (String string : strings) {
            switch (type) {
                case ACHIEVEMENT -> r.setSectionAchievement(string);
                case QUALIFICATIONS -> r.setSectionQualification(string);
            }
        }
    }

    private void writerCompanySection(Connection conn, Resume r) throws SQLException {
        String uuid = r.getUuid();
        CompanySection companySectionEducation = r.getSection(SectionType.EDUCATION);
        String jsonEducation;
        if (companySectionEducation != null) {
            jsonEducation = JsonParser.write(r.getSection(SectionType.EDUCATION), CompanySection.class);
        } else jsonEducation = null;
        CompanySection companySectionExperience = r.getSection(SectionType.EXPERIENCE);
        String jsonExperience;
        if (companySectionExperience != null) {
            jsonExperience = JsonParser.write(r.getSection(SectionType.EXPERIENCE), CompanySection.class);
        } else jsonExperience = null;
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO company_section (resume_uuid, education, experience)" +
                "                                              VALUES (?,?,?)")) {
            ps.setString(1, uuid);
            ps.setString(2, jsonEducation);
            ps.setString(3, jsonExperience);
            ps.execute();
        }

    }

    private void writerListSection(Connection conn, Resume r) throws SQLException {
        String uuid = r.getUuid();
        ListSection listSectionAchievement = r.getSection(SectionType.ACHIEVEMENT);
        String achievement;
        if (listSectionAchievement != null) {
            List<String> listAchievement = listSectionAchievement.getSectionData();
            achievement = listToText(listAchievement);
        } else {
            achievement = null;
        }
        ListSection listSectionQualification = r.getSection(SectionType.QUALIFICATIONS);
        String qualification;
        if (listSectionQualification != null) {
            List<String> listQualification = listSectionQualification.getSectionData();
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
    }

    private void writerTextSection(Connection conn, Resume r) throws SQLException {
        String uuid = r.getUuid();

        String sectionPersonal = (r.getSection(SectionType.PERSONAL) == null) ? null : r.getSection(SectionType.PERSONAL).toString();
        String sectionObjective = (r.getSection(SectionType.OBJECTIVE) == null) ? null : r.getSection(SectionType.OBJECTIVE).toString();

        sqlHelper.usePreparedStatement(conn, "INSERT INTO text_section (resume_uuid, personal , objective) VALUES (?,?,?)", ps -> {
            ps.setString(1, uuid);
            ps.setString(2, sectionPersonal);
            ps.setString(3, sectionObjective);
            ps.execute();
            return null;
        });

    }

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

/* все секции единообразно
private void addSection(ResultSet rs, Resume r) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            r.addSection(type, JsonParser.read(content, Section.class));
        }
    }

   // он же writerSection
    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                Section section = e.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
 */