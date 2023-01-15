package webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("\n Резюме " + uuid + " уже существует", uuid);
    }
}
