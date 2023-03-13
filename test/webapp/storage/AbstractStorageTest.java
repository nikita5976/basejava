package webapp.storage;

import org.junit.Before;
import org.junit.Test;
import webapp.ResumeTestData;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = "C:\\test\\resume";
    protected final Storage storage;

    protected static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    protected static final String FULL_NAME_1 = "Иванов Пётр";
    private static final String FULL_NAME_2 = "Петров Иван";
    private static final String FULL_NAME_3 = "Сидоров Николай";
    private static final String FULL_NAME_4 = "Алексеев Савелий";
    private static final String UUID_NOT_EXIST = "UUID_NOT_EXIST";
    protected static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, FULL_NAME_3);
    protected static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, FULL_NAME_4);
    private static final int TEST_SIZE = 3;

    public AbstractStorageTest(Storage storage) {
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
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(TEST_SIZE + 1);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(TEST_SIZE - 1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = new ArrayList<>();
        expected.add(ResumeTestData.createResume(UUID_1, FULL_NAME_1));
        expected.add(ResumeTestData.createResume(UUID_2, FULL_NAME_2));
        expected.add(ResumeTestData.createResume(UUID_3, FULL_NAME_3));
        List<Resume> getAll = storage.getAllSorted();
        getAll.sort(Comparator.comparing(Resume::getUuid));
        assertEquals(expected, getAll);
        assertSize(getAll.size());
    }

    @Test
    public void size() {
        assertSize(TEST_SIZE);
    }

    @Test
    public void update() {
        Resume resumeUpdated = ResumeTestData.createResume(UUID_1, FULL_NAME_1);
        storage.update(resumeUpdated);
        assertEquals(resumeUpdated, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume test = new Resume(UUID_NOT_EXIST);
        storage.update(test);
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    protected void assertGet(Resume r) {
        String uuid = r.getUuid();
        Resume rStorage = storage.get(uuid);
        assertEquals(r, rStorage);
    }
}
