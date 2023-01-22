package webapp.storage;

import org.junit.Before;
import org.junit.Test;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "UUID_NOT_EXIST";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume("uuid2");
    private static final Resume RESUME_3 = new Resume("uuid3");
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final int TEST_SIZE = 3;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertTrue(assertSize(0));
        Resume[] array = new Resume[0];
        assertArrayEquals(array, storage.getAll());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Resume r = storage.get(UUID_4);
        assertTrue(assertGet(RESUME_4));
        assertTrue(assertSize(TEST_SIZE + 1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        int stop = AbstractArrayStorage.STORAGE_LIMIT;
        try {
            for (int i = 0; i < stop; i++) {
                Resume r = new Resume(String.valueOf(i));
                storage.save(r);
            }
        } catch (StorageException e) {
            fail("overflow happened ahead of time");
        }
        storage.save(RESUME_1);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_1);
    }


    @Test
    public void get() {
        assertTrue(assertGet(RESUME_1));
        assertTrue(assertGet(RESUME_2));
        assertTrue(assertGet(RESUME_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertTrue(assertSize(TEST_SIZE - 1));
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Resume[] getAll = storage.getAll();
        assertArrayEquals(expected, getAll);
        assertTrue(assertSize(getAll.length));
    }

    @Test
    public void size() {
        assertTrue(assertSize(TEST_SIZE));
    }

    @Test
    public void update() {
        Resume resumeUpdated = new Resume(UUID_1);
        storage.update(resumeUpdated);
        assertSame(resumeUpdated, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume test = new Resume(UUID_NOT_EXIST);
        storage.update(test);
    }

    private boolean assertSize(int size) {
        return size == storage.size();
    }

    private boolean assertGet(Resume r) {
        String uuid = r.getUuid();
        Resume rStorage = storage.get(uuid);
        return r.equals(rStorage);
    }
}