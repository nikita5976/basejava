package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public void save(Resume r) {
        if (ExistStorage(r)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            addResume(r);
        }
    }

    abstract protected boolean ExistStorage(Resume r);

    abstract protected void addResume(Resume r);


}
