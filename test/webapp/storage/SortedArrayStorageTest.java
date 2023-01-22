package webapp.storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage arrayStorageTested = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super(arrayStorageTested);
    }
}