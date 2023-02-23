package webapp.model;

import java.util.*;

public class Resume {
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new HashMap<>();
    protected final Map<SectionType, AbstractSection> resumeSection = new HashMap<>();
    protected final AbstractSection<String, String> sectionObjective = new TextSection();
    protected final AbstractSection<String, String> sectionPersonal = new TextSection();
    protected final AbstractSection<String, LinkedList<String>> sectionAchievement = new ListSection();
    protected final AbstractSection<String, LinkedList<String>> sectionQualification = new ListSection();
    protected final AbstractSection<CompanySection.Company, ArrayList<CompanySection.Company>> sectionExperience = new CompanySection();
    protected final AbstractSection<CompanySection.Company, ArrayList<CompanySection.Company>> sectionEducation = new CompanySection();

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

    public void setSectionObjective(String objectiveData) {
        sectionObjective.setSectionData(objectiveData);
        resumeSection.put(SectionType.OBJECTIVE, sectionObjective);
    }

    public void setSectionPersonal(String personalData) {
        sectionPersonal.setSectionData(personalData);
        resumeSection.put(SectionType.PERSONAL, sectionPersonal);
    }

    public void setSectionAchievement(String achievementData) {
        sectionAchievement.setSectionData(achievementData);
        resumeSection.put(SectionType.ACHIEVEMENT, sectionAchievement);
    }

    public void setSectionQualification(String qualificationData) {
        sectionQualification.setSectionData(qualificationData);
        resumeSection.put(SectionType.QUALIFICATIONS, sectionQualification);
    }

    public void setSectionExperience(String dataStart, String dataStop, String name, String website, String position, String practice) {
        CompanySection.Company company = sectionExperience.getCompany(name, website);
        company.setPeriod(dataStart, dataStop);
        company.setPosition(position);
        company.setPractice(practice);
        sectionExperience.setSectionData(company);
        resumeSection.put(SectionType.EXPERIENCE, sectionExperience);
    }

    public void setSectionEducation(String dataStart, String dataStop, String name, String website, String position) {
        CompanySection.Company placeOfStudy = sectionEducation.getCompany(name, website);
        placeOfStudy.setPeriod(dataStart, dataStop);
        placeOfStudy.setPosition(position);
        sectionEducation.setSectionData(placeOfStudy);
        resumeSection.put(SectionType.EDUCATION, sectionEducation);
    }

    public Map<SectionType, AbstractSection> getResumeSection() {
        return new HashMap<>(resumeSection);
    }

    //пока без надобности
    public Object getSection(SectionType type) {
        return resumeSection.get(type);
    }


}
