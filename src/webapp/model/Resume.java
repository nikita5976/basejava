package webapp.model;

import webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Resume {
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    protected final Map<SectionType, AbstractSection> section = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }


    public void setContact(ContactType contactType, String content) {
        contacts.put(contactType, content);
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setSectionObjective(String objectiveData) {
        TextSection sectionObjective = new TextSection(objectiveData);
        section.put(SectionType.OBJECTIVE, sectionObjective);
    }

    public void setSectionPersonal(String personalData) {
        TextSection sectionPersonal = new TextSection(personalData);
        section.put(SectionType.PERSONAL, sectionPersonal);
    }

    public void setSectionAchievement(String achievementData) {
        ListSection sectionAchievement;
        if (section.get(SectionType.ACHIEVEMENT) == null) {
            ArrayList<String> al = new ArrayList<>();
            al.add(achievementData);
            sectionAchievement = new ListSection(new ArrayList<>(al));
        } else {
            sectionAchievement = (ListSection) section.get(SectionType.ACHIEVEMENT);
            sectionAchievement.getSectionData()
                    .add(achievementData);
        }
        section.put(SectionType.ACHIEVEMENT, sectionAchievement);
    }

    public void setSectionQualification(String qualificationData) {
        ListSection sectionQualification;
        if (section.get(SectionType.QUALIFICATIONS) == null) {
            ArrayList<String> al = new ArrayList<>();
            al.add(qualificationData);
            sectionQualification = new ListSection(al);
        } else {
            sectionQualification = (ListSection) section.get(SectionType.ACHIEVEMENT);
            sectionQualification.getSectionData()
                    .add(qualificationData);
        }
        section.put(SectionType.QUALIFICATIONS, sectionQualification);
    }

    public void setSectionExperience(int monthDataStart, int yearDataStart, int monthDataEnd, int yearDataEnd, String name, String website, String title, String description) {
        LocalDate dataStart = DateUtil.of(yearDataStart, Month.of (monthDataStart));
        LocalDate dataEnd = DateUtil.of(yearDataEnd,Month.of(monthDataEnd));
        CompanySection sectionExperience;
        if (section.get(SectionType.EXPERIENCE) == null) {
            ArrayList<Company> al = new ArrayList<>();
            al.add(new Company(dataStart, dataEnd, title, description, name, website));
            sectionExperience = new CompanySection(al);
        } else {
            sectionExperience = (CompanySection) section.get(SectionType.EXPERIENCE);
            sectionExperience.getSectionData()
                    .add(new Company(dataStart, dataEnd, title, description, name, website));
        }
        section.put(SectionType.EXPERIENCE, sectionExperience);
    }

    public void setSectionEducation(int monthDataStart, int yearDataStart, int monthDataEnd, int yearDataEnd, String name, String website, String title) {
        LocalDate dataStart = DateUtil.of(yearDataStart, Month.of (monthDataStart));
        LocalDate dataEnd = DateUtil.of(yearDataEnd,Month.of(monthDataEnd));
        CompanySection sectionEducation;
        if (section.get(SectionType.EDUCATION) == null) {
            ArrayList<Company> al = new ArrayList<>();
            al.add(new Company(dataStart, dataEnd, title, "", name, website));
            sectionEducation = new CompanySection(al);
        } else {
            sectionEducation = (CompanySection) section.get(SectionType.EDUCATION);
            sectionEducation.getSectionData()
                    .add(new Company(dataStart, dataEnd, title, "", name, website));
        }
        section.put(SectionType.EDUCATION, sectionEducation);
    }

    public Map<SectionType, AbstractSection> getSection() {
        return new HashMap<>(section);
    }

    //переделать
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
