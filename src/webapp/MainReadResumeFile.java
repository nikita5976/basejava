package webapp;

// чтение из файла и вывод в консоль

import webapp.model.AbstractSection;
import webapp.model.ContactType;
import webapp.model.Resume;
import webapp.model.SectionType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainReadResumeFile {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("C:\\test\\resume");
        List<Path> paths = listFiles(path);
        paths.forEach(System.out::println);

        File file = new File(String.valueOf(paths.get(0)));
        Resume testResume = doRead(file);


        Map<SectionType, AbstractSection> sectionMap = testResume.getSections();

        System.out.println(testResume.getFullName());
        System.out.println("----------------------------------------------");
        System.out.println("Контакты");
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + "  " + testResume.getContact(contactType));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println("----------------------------------------------");
            System.out.println(type.getTitle());
            System.out.println(sectionMap.get(type).toString());
        }
    }

    public static List<Path> listFiles(Path path) throws IOException {

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;
    }

    protected static Resume doRead(File file) throws IOException {
        byte[] byteResume;
        try (FileInputStream fis = new FileInputStream(file)) {
            int length = fis.available();
            byteResume = new byte[length];
            for (int i = 0; i < length; i++) {
                byteResume[i] = (byte) fis.read();
            }
        }
        return (Resume) convertBytesToObject(byteResume);
    }

    private static Object convertBytesToObject(byte[] bytesResume) {
        InputStream is = new ByteArrayInputStream(bytesResume);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException("не создался ois");
    }
}

