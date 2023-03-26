package webapp.storage.strategy;

import webapp.model.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            dos.writeUTF(resume.getSection(SectionType.PERSONAL).toString());
            dos.writeUTF(resume.getSection(SectionType.OBJECTIVE).toString());
            writeListSection(dos, SectionType.ACHIEVEMENT, resume);
            writeListSection(dos, SectionType.QUALIFICATIONS, resume);
            writeCompanySection(dos, SectionType.EXPERIENCE, resume );
            writeCompanySection(dos, SectionType.EDUCATION, resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            resume.setSectionPersonal(dis.readUTF());
            resume.setSectionObjective(dis.readUTF());
            readListSection(dis, SectionType.ACHIEVEMENT, resume);
            readListSection(dis, SectionType.QUALIFICATIONS, resume);
            readCompanySection(dis, SectionType.EXPERIENCE, resume );
            readCompanySection(dis, SectionType.EDUCATION, resume);
            return resume;
        }
    }

    private void writeListSection(DataOutputStream dos, SectionType type, Resume resume) throws IOException {
        ListSection listSection = (ListSection) resume.getSection(type);
        List<String> dataListSection = listSection.getSectionData();
        dos.writeInt(dataListSection.size());
        for (String data : dataListSection) {
            dos.writeUTF(data);
        }
    }

    private void readListSection(DataInputStream dis, SectionType type, Resume resume) throws IOException {
        int size = dis.readInt();
        switch (type) {
            case ACHIEVEMENT:
                for (int i = 0; i < size; i++) {
                    resume.setSectionAchievement(dis.readUTF());
                }
                break;
            case QUALIFICATIONS:
                for (int i = 0; i < size; i++) {
                    resume.setSectionQualification(dis.readUTF());
                }
                break;
        }
    }

    private void writeCompanySection(DataOutputStream dos, SectionType type, Resume resume) throws IOException {
        CompanySection companySection = resume.getSection(type);
        List<Company> companyList = companySection.getSectionData();
        int sizeCompanyList = companyList.size();
        dos.writeInt(sizeCompanyList);
        for (Company company : companyList) {
            dos.writeUTF(company.getName());
            dos.writeUTF(company.getWebsite());
            List<Company.Period> periodList = company.getPeriod();
            int sizePeriodList = periodList.size();
            dos.writeInt(sizePeriodList);
            for (Company.Period period : periodList) {
                dos.writeInt(period.getDateStart().getMonth().getValue());
                dos.writeInt(period.getDateStart().getYear());
                dos.writeInt(period.getDateEnd().getMonth().getValue());
                dos.writeInt(period.getDateEnd().getYear());
                dos.writeUTF(period.getTitle());
                if (type.equals(SectionType.EXPERIENCE)) {
                    dos.writeUTF(period.getDescription());
                }
            }
        }
    }

    private void readCompanySection(DataInputStream dis, SectionType type, Resume resume) throws IOException {
        int sizeCompanyList = dis.readInt();
        for (int i = 0; i < sizeCompanyList; i++) {
            String companyName = dis.readUTF();
            String companyWebsite = dis.readUTF();
            int sizePeriodList = dis.readInt();
            for (int j = 0; j < sizePeriodList; j++) {
                int monthDataStart = dis.readInt();
                int yearDataStart = dis.readInt();
                int monthDataEnd = dis.readInt();
                int yearDataEnd = dis.readInt();
                String companyTitle = dis.readUTF();
                switch (type) {
                    case EXPERIENCE:
                        String companyDescription = dis.readUTF();
                        resume.setSectionExperience(monthDataStart, yearDataStart, monthDataEnd,
                                yearDataEnd, companyName, companyWebsite, companyTitle, companyDescription);
                        break;
                    case EDUCATION:
                        resume.setSectionEducation(monthDataStart, yearDataStart, monthDataEnd,
                                yearDataEnd, companyName, companyWebsite, companyTitle);
                        break;
                }
            }
        }
    }

}
