package webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends AbstractSection<String, ArrayList<String>> {
    private final ArrayList<String> sectionData = new ArrayList<>();

    protected void setSectionData(String data) {
        sectionData.add(data);
    }

    @Override
    public ArrayList<String> getSectionData() {
        return new ArrayList<>(sectionData);
    }

    @Override
    protected String getCompany(String a, String b) {
        return null;
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
        return Objects.hash(sectionData.hashCode());
    }
}
