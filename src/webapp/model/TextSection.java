package webapp.model;

public class TextSection extends AbstractSection<String, String> {
    private String sectionData;

    @Override
    protected void setSectionData(String data) {
        sectionData = data;
    }

    @Override
    public String getSectionData() {
        return sectionData;
    }

    @Override
    protected String getCompany(String a, String b) {
        return null;
    }
}
