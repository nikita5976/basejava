package webapp.model;

import webapp.util.DateUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements  Comparable<Resume>, Serializable {
   private static final long  serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    protected final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

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

    public Map<ContactType, String> getContacts () {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSection () {
        return sections;
    }

    public void setSectionObjective(String objectiveData) {
        TextSection sectionObjective = new TextSection(objectiveData);
        sections.put(SectionType.OBJECTIVE, sectionObjective);
    }

    public void setSectionPersonal(String personalData) {
        TextSection sectionPersonal = new TextSection(personalData);
        sections.put(SectionType.PERSONAL, sectionPersonal);
    }

    public void setSectionAchievement(String achievementData) {

        setSectionList(SectionType.ACHIEVEMENT, achievementData);
    }

    public void setSectionQualification(String qualificationData) {

        setSectionList(SectionType.QUALIFICATIONS, qualificationData);
    }

    private void setSectionList(SectionType type, String data) {

        if (sections.get(type) == null) {
            ArrayList<String> al = new ArrayList<>();
            al.add(data);
            ListSection sectionList = new ListSection(new ArrayList<>(al));
            sections.put(type, sectionList);
        } else {
            ListSection sectionList = (ListSection) sections.get(type);
            sectionList.getSectionData()
                    .add(data);
        }
    }

    public void setSectionExperience(int monthDataStart, int yearDataStart, int monthDataEnd, int yearDataEnd,
                                     String name, String website, String title, String description) {

        setSectionCompany(SectionType.EXPERIENCE, monthDataStart, yearDataStart, monthDataEnd, yearDataEnd,
                name, website, title, description);
    }

    public void setSectionEducation(int monthDataStart, int yearDataStart, int monthDataEnd, int yearDataEnd,
                                    String name, String website, String title) {

        setSectionCompany(SectionType.EDUCATION, monthDataStart, yearDataStart, monthDataEnd, yearDataEnd,
                name, website, title, "");
    }

    private void setSectionCompany(SectionType type, int monthDataStart, int yearDataStart, int monthDataEnd, int yearDataEnd,
                                   String name, String website, String title, String description) {
        end:
        {
            LocalDate dataStart = DateUtil.of(yearDataStart, Month.of(monthDataStart));
            LocalDate dataEnd = DateUtil.of(yearDataEnd, Month.of(monthDataEnd));
            if (sections.get(type) == null) {
                List<Company> al = new ArrayList<>();
                Company temp = new Company(name, website);
                temp.setPeriod(dataStart, dataEnd, title, description);
                al.add(temp);
                CompanySection sectionExperience = new CompanySection(al);
                sections.put(type, sectionExperience);
            } else {
                CompanySection sectionExperience = (CompanySection) sections.get(type);
                for (Company temp : sectionExperience.getSectionData()) {
                    if (temp.getName().equals(name)) {
                        temp.setPeriod(dataStart, dataEnd, title, description);
                        break end;
                    }
                }
                Company temp = new Company(name, website);
                temp.setPeriod(dataStart, dataEnd, title, description);
                sectionExperience.getSectionData().add(temp);
            }
        }
    }

    public Map<SectionType, AbstractSection> getSections() {
        return new HashMap<>(sections);
    }

    //нужна проверка uuid на уникальность в местах хранения Resume
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return fullName + " uuid " + uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
