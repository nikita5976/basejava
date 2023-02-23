package webapp.model;

import java.util.LinkedList;

public class ListSection extends AbstractSection<String, LinkedList<String>> {
    private final LinkedList<String> sectionData = new LinkedList<>();

    protected void setSectionData(String data) {
        sectionData.addLast(data);
    }

    @Override
    public LinkedList<String> getSectionData() {
        return new LinkedList<>(sectionData);
    }

    @Override
    protected String getCompany(String a, String b) {
        return null;
    }
}
