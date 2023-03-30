package webapp.storage.strategy;

import webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
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

            contacts.forEach((key, value) -> {
                try {
                    dos.writeUTF(key.name());
                    dos.writeUTF(value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            /* старый  вариант
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
             */
            writeTextSection(dos, SectionType.PERSONAL, resume);
            writeTextSection(dos, SectionType.OBJECTIVE, resume);
            writeListSection(dos, SectionType.ACHIEVEMENT, resume);
            writeListSection(dos, SectionType.QUALIFICATIONS, resume);
            writeCompanySection(dos, SectionType.EXPERIENCE, resume);
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
            readTextSection(dis, SectionType.PERSONAL, resume);
            readTextSection(dis, SectionType.OBJECTIVE, resume);
            readListSection(dis, SectionType.ACHIEVEMENT, resume);
            readListSection(dis, SectionType.QUALIFICATIONS, resume);
            readCompanySection(dis, SectionType.EXPERIENCE, resume);
            readCompanySection(dis, SectionType.EDUCATION, resume);
            return resume;
        }
    }


    private void writeTextSection(DataOutputStream dos, SectionType type, Resume resume) throws IOException {
        TextSection textSection = resume.getSection(type);
        if (textSection == null) {
            dos.writeBoolean(false);
            return;
        } else dos.writeBoolean(true);
        String dataTextSection = textSection.getSectionData();
        dos.writeUTF(dataTextSection);
    }

    private void readTextSection(DataInputStream dis, SectionType type, Resume resume) throws IOException {
        if (dis.readBoolean()) {
        } else return;
        switch (type) {
            case PERSONAL:
                resume.setSectionPersonal(dis.readUTF());
                return;
            case OBJECTIVE:
                resume.setSectionObjective(dis.readUTF());
        }
    }

    private void writeListSection(DataOutputStream dos, SectionType type, Resume resume) throws IOException {
        ListSection listSection = (ListSection) resume.getSection(type);
        if (listSection == null) {
            dos.writeBoolean(false);
            return;
        } else dos.writeBoolean(true);
        List<String> dataListSection = listSection.getSectionData();
        dos.writeInt(dataListSection.size());

        anyWriter(dos, dataListSection);



       /*  старый вариант
       for (String data : dataListSection) {
            dos.writeUTF(data);
        }
        */
    }

    private void readListSection(DataInputStream dis, SectionType type, Resume resume) throws IOException {
        if (dis.readBoolean()) {
        } else return;
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
        if (companySection == null) {
            dos.writeBoolean(false);
            return;
        } else dos.writeBoolean(true);
        List<Company> companyList = companySection.getSectionData();
        int sizeCompanyList = companyList.size();
        dos.writeInt(sizeCompanyList);
        for (Company company : companyList) {
            dos.writeUTF(company.getName());
            setFlagDoesNotExistString(dos, company.getWebsite());
            List<Company.Period> periodList = company.getPeriod();
            int sizePeriodList = periodList.size();
            dos.writeInt(sizePeriodList);
            for (Company.Period period : periodList) {
                writeDate(dos, period.getDateStart());
                writeDate(dos, period.getDateEnd());
                dos.writeUTF(period.getTitle());
                if (type.equals(SectionType.EXPERIENCE)) {
                    setFlagDoesNotExistString(dos, period.getDescription());
                }
            }
        }
    }

    private void readCompanySection(DataInputStream dis, SectionType type, Resume resume) throws IOException {
        if (dis.readBoolean()) {
        } else return;
        int sizeCompanyList = dis.readInt();
        for (int i = 0; i < sizeCompanyList; i++) {
            String companyName = dis.readUTF();
            String companyWebsite = dis.readUTF();
            if (companyWebsite.equals("null")) {
                companyWebsite = null;
            }
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
                        if (companyDescription.equals("null")) {
                            companyDescription = null;
                        }
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
     interface DataWriter  <T> {
        void writer(DataOutputStream dos, T collection ) throws IOException ;
    }
    public void anyWriter(DataOutputStream dos, Collection<String> collection) throws IOException {

        for (String element : collection) {
            dos.writeUTF(element);
        }
    }

}
