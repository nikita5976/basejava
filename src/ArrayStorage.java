import java.util.Arrays;

/**
 * Array based storage for Resumes
 * методы реализованы без проверки uuid на уникальность
 * при добавлении Resume заносится в следующую свободную справа ячейку массива storage
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    // вел переменную контроля заполнения массива значениями при пустом массиве значение -1,
    // соответствует значению индекса последней заполненной ячейки массива
    private int indResume = -1;

    void clear() {
        for (int i = 0; i <= indResume; i++) {
            storage[i] = null;
        }
        indResume = -1;
    }

    void save(Resume r) {
        if (indResume == 9999) {
            return; //защита от обращения сверх длинны массива
        } else {
            storage[++indResume] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i <= indResume; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i <= indResume; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j <= indResume; j++) {
                    if (j == indResume){   //защита от обращения сверх длинны массива
                        storage[j]=null;
                    } else {
                        storage[j] = storage[j + 1];
                    }
                }
                indResume--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, indResume +1);
        //new Resume[0];
    }

    int size() {
        return indResume + 1;
    }
}
