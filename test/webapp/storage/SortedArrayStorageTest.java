package webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage arrayStorageTested = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super(arrayStorageTested);
    }
}