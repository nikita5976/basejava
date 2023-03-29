package webapp.storage.strategy;

import java.io.DataOutputStream;
import java.io.IOException;

public class AnyDataWrite implements DataWriter{
    @Override
    public void writer(DataOutputStream dos, String element) throws IOException {
        dos.writeUTF(element);
    }
}
