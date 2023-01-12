package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size == 0) {
            storage[0] = r;
            size++;
            return;
        }
        if (size >= storage.length) {
            System.out.println("\n резюме " + r.getUuid() + " не сохранено, архив полон");
            return;
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("\n Резюме " + r.getUuid() + " уже существует");
            return;
        }
        if (-index == size + 1) {
            storage[size] = r;
            size++;
        } else {
            index = -index - 1;
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("\n резюме " + uuid + " не было в архиве");
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
