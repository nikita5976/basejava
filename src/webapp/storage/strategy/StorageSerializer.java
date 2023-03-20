package webapp.storage.strategy;

import webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public interface StorageSerializer {

     void doWrite(Resume r, OutputStream os) throws IOException;

     Resume doRead(InputStream is) throws IOException;
}
