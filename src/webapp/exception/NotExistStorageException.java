package webapp.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid) {
        super("\n резюме " + uuid + " не было в архиве", uuid);
    }
}
