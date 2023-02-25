package webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
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

