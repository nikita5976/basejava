package webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends AbstractSection {
    private static final long  serialVersionUID = 1L;
    private  List<String> sectionData;

    public ListSection() {}

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
