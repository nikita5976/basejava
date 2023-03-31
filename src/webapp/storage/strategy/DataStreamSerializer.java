
package webapp.storage.strategy;

import webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            contacts.forEach((key, value) -> {
                try {
                    dos.writeUTF(key.name());
                    dos.writeUTF(value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            Map<SectionType, AbstractSection> section = resume.getSections();
            dos.writeInt(section.size());


            for (Map.Entry<SectionType, AbstractSection> entry : section.entrySet()) {
                SectionType type = entry.getKey();
                AbstractSection sections = entry.getValue();
                dos.writeUTF(type.name());

                switch (type) {
                    case OBJECTIVE, PERSONAL:
                        dos.writeUTF(((TextSection) sections).getSectionData());
                        break;
                    case ACHIEVEMENT, QUALIFICATIONS:
                        List<String> dataListSection = ((ListSection) sections).getSectionData();
                        dos.writeInt(dataListSection.size());
                        for (String data : dataListSection) {
                            dos.writeUTF(data);
                        }
                        break;
                    case EXPERIENCE, EDUCATION:
                        List<Company> companyList = ((CompanySection) sections).getSectionData();
                        dos.writeInt(companyList.size());
                        for (Company company : companyList) {
                            dos.writeUTF(company.getName());
                            setFlagDoesNotExistString(dos, company.getWebsite());
                            List<Company.Period> periodList = company.getPeriod();
                            dos.writeInt(periodList.size());
                            for (Company.Period period : periodList) {
                                writeDate(dos, period.getDateStart());
                                writeDate(dos, period.getDateEnd());
                                dos.writeUTF(period.getTitle());
                                if (type.equals(SectionType.EXPERIENCE)) {
                                    String description = period.getDescription();
                                    dos.writeUTF(Objects.requireNonNullElse(description, "null"));
                                }
                            }
                        }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSections = dis.readInt();
            for (int j = 0; j < sizeSections; j++) {
                String sectionType = dis.readUTF();
                switch (sectionType) {
                    case "PERSONAL":
                        resume.setSectionPersonal(dis.readUTF());
                        break;
                    case "OBJECTIVE":
                        resume.setSectionObjective(dis.readUTF());
                        break;
                    case "ACHIEVEMENT":
                        int sizeAch = dis.readInt();
                        for (int i = 0; i < sizeAch; i++) {
                            resume.setSectionAchievement(dis.readUTF());
                        }
                        break;
                    case "QUALIFICATIONS":
                        int size = dis.readInt();
                        for (int i = 0; i < size; i++) {
                            resume.setSectionQualification(dis.readUTF());
                        }
                        break;
                    case "EXPERIENCE","EDUCATION":
                        int sizeCompanyList = dis.readInt();
                        for (int i = 0; i < sizeCompanyList; i++) {
                            String companyName = dis.readUTF();
                            String companyWebsite = dis.readUTF();
                            if (companyWebsite.equals("null")) {
                                companyWebsite = null;
                            }
                            int sizePeriodList = dis.readInt();
                            for (int ji = 0; ji < sizePeriodList; ji++) {
                                int monthDataStart = dis.readInt();
                                int yearDataStart = dis.readInt();
                                int monthDataEnd = dis.readInt();
                                int yearDataEnd = dis.readInt();
                                String companyTitle = dis.readUTF();
                                if (sectionType.equals("EXPERIENCE")) {
                                    String companyDescription = dis.readUTF();
                                    if (companyDescription.equals("null")) {
                                        companyDescription = null;
                                    }
                                    resume.setSectionExperience(monthDataStart, yearDataStart, monthDataEnd,
                                            yearDataEnd, companyName, companyWebsite, companyTitle, companyDescription);
                                } else {
                                    resume.setSectionEducation(monthDataStart, yearDataStart, monthDataEnd,
                                            yearDataEnd, companyName, companyWebsite, companyTitle);
                                }
                            }
                        }
                        break;
                }
            }
            return resume;
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getMonth().getValue());
        dos.writeInt(date.getYear());
    }

    private void setFlagDoesNotExistString(DataOutputStream dos, String string) throws IOException {
        if (string == null) {
            dos.writeUTF("null");
        } else dos.writeUTF(string);
    }

    @FunctionalInterface
    interface DataWriter<T> {
        void writer(DataOutputStream dos, T collection) throws IOException;
    }

    void anyWriter(DataOutputStream dos, Collection<String> collection) throws IOException {

        for (String element : collection) {
            dos.writeUTF(element);
        }
    }
}


