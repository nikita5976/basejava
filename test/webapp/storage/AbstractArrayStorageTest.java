package webapp.storage;

import org.junit.Before;
import org.junit.Test;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private final Storage arrayStorageTested;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private final Resume resume = new Resume(UUID_1);

    public AbstractArrayStorageTest(Storage arrayStorageTested) {
        this.arrayStorageTested = arrayStorageTested;
    }

    @Before
    public void setUp() {
        arrayStorageTested.clear();
        arrayStorageTested.save(resume);
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
        Resume test = new Resume("TEST");
        arrayStorageTested.save(test);
        Resume r = arrayStorageTested.get("TEST");
        assertSame(test, r);
        Resume[] arrayAll = arrayStorageTested.getAll();
        assertEquals(4, arrayAll.length);
    }

    @Test
    public void saveNotFullStorage() {
        // some code
    }

    @Test(expected = StorageException.class)
    public void saveFullStorage() {
        // some code
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        // some code
    }


    @Test
    public void get() {
        assertSame(resume, arrayStorageTested.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        arrayStorageTested.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        arrayStorageTested.delete(UUID_1);
        Resume[] arrayAll = arrayStorageTested.getAll();
        assertEquals(2, arrayAll.length);
        arrayStorageTested.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorage() {
        arrayStorageTested.delete("some");
    }

    @Test
    public void getAll() {
        Resume[] arrayAll = arrayStorageTested.getAll();
        assertEquals(3, arrayAll.length);
    }

    @Test
    public void size() {
        assertEquals(3, arrayStorageTested.size());
    }

    @Test
    public void update() {
        Resume testUuid_2 = new Resume(UUID_2);
        arrayStorageTested.update(testUuid_2);
        assertSame(testUuid_2, arrayStorageTested.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        Resume test = new Resume("TEST");
        arrayStorageTested.update(test);
    }
}