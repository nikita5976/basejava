package webapp.model;

import java.util.Objects;
import java.util.UUID;

public class Resume {
    private final String uuid;
    private final String fullName;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        // разобраться с проверкой когда будет возможно обеспечить uuid всегда не null на входе
        //решить аналогичный вопрос с методами delete и update
        if (uuid == null) {
            this.uuid = (UUID.randomUUID().toString());
        } else {
            this.uuid = uuid;
        }
        this.fullName = new GeneratorNames().getFullName();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return fullName + " uuid " + uuid;
    }


}
