package webapp.storage;

import org.junit.Test;
import webapp.storage.AbstractArrayStorageTest;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage arrayStorageTested = new ArrayStorage();

    public ArrayStorageTest() {
        super(arrayStorageTested);
    }

}