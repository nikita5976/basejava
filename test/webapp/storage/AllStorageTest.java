package webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        FullNameMapStorageTest.class,
        MapResumeStorageTest.class,
        FileStoregeTest.class
})
public class AllStorageTest {
}
