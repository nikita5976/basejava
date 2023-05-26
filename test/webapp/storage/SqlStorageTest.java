package webapp.storage;

import webapp.Config;

public class SqlStorageTest extends AbstractStorageTest{


    static final String dbUrl ;
    static final String dbUser ;
    static final String dbPassword ;

    static {
        dbUrl = Config.get().getDbUrl();
        dbUser = Config.get().getDbUser();
        dbPassword = Config.get().getDbPassword();
    }

    public SqlStorageTest() {
        super(new SqlStorage(dbUrl, dbUser, dbPassword));
    }
}
