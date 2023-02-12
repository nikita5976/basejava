package webapp.storage;

import org.junit.Test;
import webapp.exception.ExistStorageException;
import webapp.model.Resume;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class FullNameMapStorageTest extends AbstractStorageTest {
    public FullNameMapStorageTest() {
        super(new FullNameMapStorage());
    }


    @Override
    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_1);
    }

    @Override
    @Test
    public void update() {
        Resume resumeUpdated = new Resume(UUID_1, FULL_NAME_1);
        storage.update(resumeUpdated);
        assertSame(resumeUpdated, storage.get(FULL_NAME_1));
        assertSame(resumeUpdated, storage.get(UUID_1));
    }

    @Override
    protected void assertGet(Resume r) {
        String fullName = r.getFullName();
        Resume rStorage = storage.get(fullName);
        assertEquals(r, rStorage);
    }

}
