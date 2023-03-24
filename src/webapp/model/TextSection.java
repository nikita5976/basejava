package webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class TextSection extends AbstractSection {
    private static final long  serialVersionUID = 1L;
    private final String sectionData;

    protected TextSection(String sectionData) {
        Objects.requireNonNull(sectionData, "sectionData  TextSection must not be null");
        this.sectionData = sectionData;
    }

    public String getSectionData() {
        return sectionData;
    }

    @Override
    public String toString() {
        return sectionData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection textSection = (TextSection) o;
        return sectionData.equals(textSection.sectionData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionData);
    }
}

