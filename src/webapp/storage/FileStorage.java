package webapp.storage;

import webapp.model.Resume;

import java.io.*;

public class FileStorage extends AbstractFileStorage {
    protected FileStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, File file) throws IOException {
        byte[] byteResume = convertObjectToBytes(r);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteResume);
        }
    }

    @Override
    protected Resume doRead(File file) throws IOException {
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

    private byte[] convertObjectToBytes(Object resume) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(resume);
            return boas.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }

    private Object convertBytesToObject(byte[] bytesResume) {
        InputStream is = new ByteArrayInputStream(bytesResume);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }
}

