package webapp.model;

import java.util.*;

public class Resume {
    private String uuid;
    private String fullName;
    private final TextSection objective = new TextSection();
    private final TextSection personal = new TextSection();
    private final ListSection achievement = new ListSection();
    private final ListSection qualifications = new ListSection();
    private final ArrayListSection experience = new ArrayListSection();
    private final ArrayListSection education = new ArrayListSection();

    private final Map<ContactType, String> contacts = new HashMap<>();

    public Resume() {

    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
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

    public void setContact(ContactType contactType, String content) {
        contacts.put(contactType, content);
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public String[] getSection(SectionType section) {
        String[] dataSection;
        switch (section) {
            case OBJECTIVE:
                dataSection = objective.getSectionData();
                break;
            case PERSONAL:
                dataSection = personal.getSectionData();
                break;
            case ACHIEVEMENT:
                dataSection = achievement.getSectionData();
                break;
            case QUALIFICATIONS:
                dataSection = qualifications.getSectionData();
                break;
            case EDUCATION:
                dataSection = education.getSectionData();
                break;
            case EXPERIENCE:
                dataSection = experience.getSectionData();
                break;
            default:
                dataSection = null;
        }
        return dataSection;
    }

    public int getSectionSize(SectionType section) {
        int size;
        switch (section) {
            case OBJECTIVE:
                size = objective.getSize();
                break;
            case PERSONAL:
                size = personal.getSize();
                break;
            case ACHIEVEMENT:
                size = achievement.getSize();
                break;
            case QUALIFICATIONS:
                size = qualifications.getSize();
                break;
            case EDUCATION:
                size = education.getSize();
                break;
            case EXPERIENCE:
                size = experience.getSize();
                break;
            default:
                size = 0;
        }
        return size;
    }

    public void setSection(SectionType section, String[] data) {
        switch (section) {
            case OBJECTIVE:
                objective.setSectionData(data);
                break;
            case PERSONAL:
                personal.setSectionData(data);
                break;
            case ACHIEVEMENT:
                achievement.setSectionData(data);
                break;
            case QUALIFICATIONS:
                qualifications.setSectionData(data);
                break;
            case EDUCATION:
                education.setSectionData(data);
                break;
            case EXPERIENCE:
                experience.setSectionData(data);
                break;
        }
    }

    private final class TextSection extends AbstractSection {
        private final StringBuilder sectionData = new StringBuilder();

        protected void setSectionData(String[] inData) {
            sectionData.append(inData[0]);
        }

        protected String[] getSectionData() {
            String[] a = {sectionData.toString()};
            return a;
        }

        protected int getSize() {
            return 1;
        }

    }

    private final class ListSection extends AbstractSection {
        private final LinkedList<String> sectionData = new LinkedList<>();

        protected void setSectionData(String[] inData) {
            sectionData.addLast(inData[0]);
        }

        protected String[] getSectionData() {
            String temp = sectionData.pollFirst();
            sectionData.addLast(temp);
            String[] a = {temp};
            return a;
        }

        protected int getSize() {
            return sectionData.size();
        }
    }

    private final class ArrayListSection extends AbstractSection {

        private final LinkedList<String[]> sectionData = new LinkedList<>();

        protected void setSectionData(String[] data) {
            sectionData.addLast(data);
        }

        protected String[] getSectionData() {
            String[] data = sectionData.pollFirst();
            sectionData.addLast(data);
            return data;
        }

        protected int getSize() {
            return sectionData.size();
        }
    }
}
