
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
            writeWithException(dos, contacts.entrySet(), mapContact -> {
                dos.writeUTF(mapContact.getKey().toString());
                dos.writeUTF(mapContact.getValue());
            });

            Map<SectionType, AbstractSection> section = resume.getSections();
            writeWithException(dos, section.entrySet(), mapSection -> {
                SectionType type = mapSection.getKey();
                AbstractSection sections = mapSection.getValue();
                dos.writeUTF(type.name());

                switch (type) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) sections).getSectionData());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeWithException(dos, ((ListSection) sections).getSectionData(), dos::writeUTF);
                    case EXPERIENCE, EDUCATION -> {
                        writeWithException(dos, ((CompanySection) sections).getSectionData(), dataCompany -> {
                            dos.writeUTF(dataCompany.getName());
                            setFlagDoesNotExistString(dos, dataCompany.getWebsite());
                            writeWithException(dos, dataCompany.getPeriod(), dataPeriod -> {
                                writeDate(dos, dataPeriod.getDateStart());
                                writeDate(dos, dataPeriod.getDateEnd());
                                dos.writeUTF(dataPeriod.getTitle());
                                String description = dataPeriod.getDescription();
                                dos.writeUTF(Objects.requireNonNullElse(description, "null"));
                            });
                        });
                    }
                }
            });
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, resume, res -> res.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, resume, res -> {
                String sectionType = dis.readUTF();
                switch (sectionType) {
                    case "PERSONAL" -> resume.setSectionPersonal(dis.readUTF());
                    case "OBJECTIVE" -> resume.setSectionObjective(dis.readUTF());
                    case "ACHIEVEMENT" ->
                            readWithException(dis, resume, res3 -> res.setSectionAchievement(dis.readUTF()));
                    case "QUALIFICATIONS" ->
                            readWithException(dis, resume, res3 -> res.setSectionQualification(dis.readUTF()));
                    case "EXPERIENCE", "EDUCATION" -> {
                        readWithException(dis, resume, res1 -> {
                            String companyName = dis.readUTF();
                            String tempCompanyWebsite = dis.readUTF();
                            String companyWebsite = (tempCompanyWebsite.equals("null")) ? null : tempCompanyWebsite;
                            readWithException(dis, resume, res2 -> {
                                int[] date = new int[4];
                                readeDate(dis, date);
                                String companyTitle = dis.readUTF();
                                String companyDescription = dis.readUTF();
                                if (companyDescription.equals("null")) {
                                    companyDescription = null;
                                }
                                if (sectionType.equals("EXPERIENCE")) {
                                    resume.setSectionExperience(date[0], date[1], date[2],
                                            date[3], companyName, companyWebsite, companyTitle, companyDescription);
                                } else {
                                    resume.setSectionEducation(date[0], date[1], date[2],
                                            date[3], companyName, companyWebsite, companyTitle);
                                }
                            });
                        });
                    }
                }
            });
            return resume;
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getMonth().getValue());
        dos.writeInt(date.getYear());
    }

    private void readeDate(DataInputStream dis, int[] data) throws IOException {
        for (int i=0;  i< data.length; i++){
            data[i] = dis.readInt();
        }
    }

    private void setFlagDoesNotExistString(DataOutputStream dos, String string) throws IOException {
        dos.writeUTF(Objects.requireNonNullElse(string, "null"));
    }

    @FunctionalInterface
    interface DataWriter<T> {
        void write(T t) throws IOException;
    }

    public <T> void writeWithException(DataOutputStream dos, Collection<T> collection, DataWriter<T> writer) throws IOException {

        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    @FunctionalInterface
    interface DataReader {
        void read(Resume resume) throws IOException;
    }

    public void readWithException(DataInputStream dis, Resume resume, DataReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read(resume);
        }
    }
}




