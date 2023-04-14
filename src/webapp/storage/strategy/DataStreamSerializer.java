
package webapp.storage.strategy;

import webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
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
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) sections).getSectionData());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeWithException(dos, ((ListSection) sections).getSectionData(), dos::writeUTF);
                    case EXPERIENCE, EDUCATION -> {
                        writeWithException(dos, ((CompanySection) sections).getSectionData(), dataCompany -> {
                            dos.writeUTF(dataCompany.getName());
                            setFlagDoesNotExistString(dos, dataCompany.getWebsite());
                            writeWithException(dos,  dataCompany.getPeriod(), dataPeriod  -> {
                                writeDate(dos, dataPeriod.getDateStart());
                                writeDate(dos, dataPeriod.getDateEnd());
                                dos.writeUTF(dataPeriod.getTitle());
                                if (type.equals(SectionType.EXPERIENCE)) {
                                    String description = dataPeriod.getDescription();
                                    dos.writeUTF(Objects.requireNonNullElse(description, "null"));
                                }
                            });
                        });
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
                    case "PERSONAL" -> resume.setSectionPersonal(dis.readUTF());
                    case "OBJECTIVE" -> resume.setSectionObjective(dis.readUTF());
                    case "ACHIEVEMENT" -> {
                        int sizeAch = dis.readInt();
                        for (int i = 0; i < sizeAch; i++) {
                            resume.setSectionAchievement(dis.readUTF());
                        }
                    }
                    case "QUALIFICATIONS" -> {
                        int size = dis.readInt();
                        for (int i = 0; i < size; i++) {
                            resume.setSectionQualification(dis.readUTF());
                        }
                    }
                    case "EXPERIENCE", "EDUCATION" -> {
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
                    }
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
        dos.writeUTF(Objects.requireNonNullElse(string, "null"));
    }

    @FunctionalInterface
    interface DataWriter<T> {
        void write( T t) throws IOException;
    }

   public <T> void writeWithException(DataOutputStream dos, Collection<T> collection, DataWriter<T> writer) throws IOException {

       dos.writeInt(collection.size());
       for (T t : collection) {
           writer.write(t);
       }
    }
}




