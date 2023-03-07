package webapp.storage;

/*
public class TempFileStorage {
    @Override
    protected void doSave(Resume r, Path searchKey) {

        if (!Files.isDirectory(DIR)) {
            try {
                Files.createDirectory(DIR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        byte[] byteResume = convertObjectToBytes(r);

        try {
            Files.write(searchKey, byteResume);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Resume doGet(Path searchKey) {
        byte[] bytesResume;
        try {
            bytesResume = Files.readAllBytes(searchKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (Resume) convertBytesToObject(bytesResume);
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doUpdate(Path searchKey, Resume r) {
        doSave(r, searchKey);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        String fileName = DIR_SAVE + "\\" + uuid;
        return Paths.get(fileName);
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumeList = new ArrayList<>();
        for (Path path : getListPath()) {
            resumeList.add(doGet(path));
        }
        return resumeList;
    }

    @Override
    public void clear() {
        if (Files.exists(DIR)) {
            try (Stream<Path> walk = Files.walk(DIR)) {
                walk
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int size() {
        if (!Files.exists(DIR)) {
            return 0;
        }
        return getListPath().size();
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

    private List<Path> getListPath() {
        List<Path> pathResumes;
        try (Stream<Path> walk = Files.walk(DIR)) {
            pathResumes = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pathResumes;
    }
}

 */

