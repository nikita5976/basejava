/**
 * Array based storage for Resumes
 * методы реализованы без проверки uuid на уникальность
 * при добавлении Resume заносится в следующую свободную справа ячейку массива storage
 * защиты от переполнения нет исключение возникнет при добавлении 10001 элемента
 * или удалении из массива заполненного до 10000 ячеек
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    // вел переменную контроля количества внесённых Resume,
    // соответствует значению индекса последней заполненной ячейки массива
    private int amtResume = -1;

    void clear() {
        for (int i = 0; i <= amtResume; i++) {
            storage[i] = null;
        }
        amtResume = -1;
    }

    void save(Resume r) {
        storage[++amtResume] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i <= amtResume; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i <= amtResume; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j <= amtResume; j++) {
                    storage[j] = storage[j + 1];// есть риск уехать за 10000
                }
                amtResume--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] getResume = new Resume[amtResume + 1];
        for (int i = 0; i <= amtResume; i++) {
            getResume[i] = storage[i];
        }
        return getResume;
        //new Resume[0];
    }

    int size() {
        return amtResume + 1;
    }
}
