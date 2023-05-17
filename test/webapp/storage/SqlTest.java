package webapp.storage;

import webapp.Config;

public class SqlTest extends AbstractStorageTest{

    static final String dbUrl ;
    static final String dbUser ;
    static final String dbPassword ;

    static {
        dbUrl = Config.get().getDbUrl();
        dbUser = Config.get().getDbUser();
        dbPassword = Config.get().getDbPassword();
    }

    public SqlTest() {
        super(new SqlStorage(dbUrl, dbUser, dbPassword));
    }
}
