import java.util.Arrays;

/**
 * Array based storage for Resumes
 * методы реализованы без проверки uuid на уникальность
 * при добавлении Resume заносится в следующую свободную справа ячейку массива storage
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    // ввел переменную контроля заполнения массива значениями при пустом массиве значение 0,
    // соответствует количеству заполненных ячеек заполненной ячейки массива
    private int countResumes;

    void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    void save(Resume r) {
        if (countResumes >= storage.length) {
            System.out.println("----------------------------\n" + "данные не сохранены, архив Resume полон");
            return;
        }
        storage[countResumes++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                if (i != (countResumes - 1)) {
                    storage[i] = storage[countResumes - 1];
                }
                storage[countResumes - 1] = null;
                countResumes--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    int size() {
        return countResumes;
    }
}
