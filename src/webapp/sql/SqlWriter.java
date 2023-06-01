package webapp.sql;

import webapp.model.ContactType;
import webapp.model.Resume;

import java.sql.PreparedStatement;
import java.util.Map;

 public  class SqlWriter {
    public static void sqlWriter(String action_1, String action_2, SqlHelper sqlHelper, Resume r, ABlockOfCode aBlockOfCode) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(action_1)) {
                ps.setString(2, r.getUuid());
                ps.setString(1, r.getFullName());
               aBlockOfCode.execute(ps);
            }

            try (PreparedStatement ps = conn.prepareStatement(action_2)) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(2, r.getUuid());
                    ps.setString(3, e.getKey().name());
                    ps.setString(1, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }
}
