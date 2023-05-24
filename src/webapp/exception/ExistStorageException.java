package webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("\n Резюме " + uuid + " уже существует", uuid);
    }

    public ExistStorageException (String uuid, Exception e){
        super("\n Резюме " + uuid + "возможно уже существует", e);

    }
}
