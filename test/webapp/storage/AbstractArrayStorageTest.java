package webapp.storage;

import org.junit.Test;
import webapp.exception.StorageException;
import webapp.model.Resume;

import static org.junit.Assert.fail;
import static webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                Resume r = new Resume(String.valueOf(i));
                storage.save(r);
            }
        } catch (StorageException e) {
            fail("overflow happened ahead of time");
        }
        storage.save(RESUME_1);
    }

}