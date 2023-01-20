package webapp.storage;

import org.junit.Before;
import org.junit.Test;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AbstractArrayStorageTest {
    private final Storage arrayStorageTested;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage arrayStorageTested) {
        this.arrayStorageTested = arrayStorageTested;
    }

    @Before
    public void setUp()  {
        arrayStorageTested.clear();
        arrayStorageTested.save(new Resume(UUID_1));
        arrayStorageTested.save(new Resume(UUID_2));
        arrayStorageTested.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        arrayStorageTested.clear();
        Resume[] arrayAll = arrayStorageTested.getAll();
        for (Resume r : arrayAll) {
            assertNull(r);
        }
    }

    @Test
    public void save() {
    }

    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void size() {
        assertEquals(3, arrayStorageTested.size());
    }

    @Test
    public void update() {
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist()  {
        arrayStorageTested.get("dummy");
    }
}