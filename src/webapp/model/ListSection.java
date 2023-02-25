package webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> sectionData;

    protected ListSection(List<String> sectionData) {
        Objects.requireNonNull(sectionData, "sectionData in ListSection must not be null");
        this.sectionData = sectionData;
    }

    public List<String> getSectionData() {
        return sectionData;
    }

    @Override
    public String toString() {
        return sectionData.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection listSection = (ListSection) o;
        return sectionData.equals(listSection.sectionData);
    }

    @Override
    public int hashCode() {
        return sectionData.hashCode();
    }
}
